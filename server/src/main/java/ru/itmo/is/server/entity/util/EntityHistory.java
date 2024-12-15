package ru.itmo.is.server.entity.util;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.security.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "entity_history")
@Getter
@Setter
@SequenceGenerator(name = "entityHistorySeq", sequenceName = "entity_history_id_seq", allocationSize = 1)
public class EntityHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entityHistorySeq")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private AbstractEntity entity;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "login")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EntityHistory() { }

    public EntityHistory(AbstractEntity entity, User updatedBy) {
        this.entity = entity;
        this.updatedBy = updatedBy;
        this.updatedAt = LocalDateTime.now();
    }
}
