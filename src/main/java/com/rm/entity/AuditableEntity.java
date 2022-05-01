package com.rm.entity;

import com.rm.listener.AuditListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    private Instant updatedAt;

    private Instant updatedBy;
}
