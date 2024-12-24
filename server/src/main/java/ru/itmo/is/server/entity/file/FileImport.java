package ru.itmo.is.server.entity.file;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.security.User;

@Entity
@Table(name = "file_import")
@Getter
@Setter
@SequenceGenerator(name = "fileImportSeq", sequenceName = "file_import_id_seq", allocationSize = 1)
@NamedQuery(name = "FileImport.getNameByKey", query = "SELECT fi.fileName FROM FileImport fi WHERE fi.downloadKey = :key")
@NamedQuery(name = "FileImport.count", query = "SELECT COUNT(fi) FROM FileImport fi WHERE fi.createdBy = :owner")
public class FileImport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileImportSeq")
    private long id;

    @NotBlank
    @Column(name = "file_name", nullable = false, updatable = false)
    private String fileName;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Enumerated(EnumType.STRING)
    private ImportStatus status;

    @Column(name = "inserted_people")
    private Integer insertedPeople;

    @Column(name = "inserted_coordinates")
    private Integer insertedCoordinates;

    @Column(name = "inserted_locations")
    private Integer insertedLocations;

    @Column(name = "download_key")
    private String downloadKey;

    public void success(String downloadKey, InsertCounter counter) {
        this.downloadKey = downloadKey;
        this.status = ImportStatus.COMPLETED;
        addCounter(counter);
    }

    public void validationFailed() {
        noInsert(ImportStatus.VALIDATION_FAILED);
    }

    public void insertFailed() {
        noInsert(ImportStatus.SAVE_DATA_FAILED);
    }

    private void noInsert(ImportStatus status) {
        this.downloadKey = null;
        this.status = status;
        addCounter(null);
    }

    private void addCounter(@Nullable  InsertCounter counter) {
        if (counter == null) {
            this.insertedPeople = null;
            this.insertedCoordinates = null;
            this.insertedLocations = null;
        } else {
            this.insertedPeople = counter.getInsertedPeople();
            this.insertedCoordinates = counter.getInsertedCoordinates();
            this.insertedLocations = counter.getInsertedLocation();
        }
    }

    public static FileImport of(String fileName, User createdBy) {
        var fi = new FileImport();
        fi.setFileName(fileName);
        fi.setCreatedBy(createdBy.getLogin());
        fi.setStatus(ImportStatus.IN_PROGRESS);
        return fi;
    }
}
