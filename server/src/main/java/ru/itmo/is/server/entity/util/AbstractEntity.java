package ru.itmo.is.server.entity.util;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.itmo.is.server.entity.security.User;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AbstractEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "login", nullable = false, updatable = false)
    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "login")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "admin_access", nullable = false)
    private boolean adminAccess;
}
