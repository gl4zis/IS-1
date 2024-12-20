package ru.itmo.is.server.entity.util;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.security.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "abstract_entity")
@Getter
@Setter
@EntityListeners(EntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "abstractEntitySeq", sequenceName = "abstract_entity_id_seq", allocationSize = 1)
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abstractEntitySeq")
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "login", nullable = false, updatable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "admin_access", nullable = false, updatable = false)
    private boolean adminAccess;

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "id")
    private List<EntityHistory> history;
}
