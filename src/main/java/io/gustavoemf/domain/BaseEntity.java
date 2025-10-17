package io.gustavoemf.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.Instant;

@MappedSuperclass
public class BaseEntity extends PanacheEntity {

    @Column(nullable = false, updatable = false)
    public Instant createdAt;

    @Column(nullable = false, updatable = false)
    public Instant updatedAt;

    @Column(nullable = false)
    public boolean active = true;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}
