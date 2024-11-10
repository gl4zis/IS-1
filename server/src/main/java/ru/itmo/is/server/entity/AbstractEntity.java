package ru.itmo.is.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;
}
