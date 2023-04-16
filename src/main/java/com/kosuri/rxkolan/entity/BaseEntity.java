package com.kosuri.rxkolan.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    protected LocalDateTime created;

    @LastModifiedDate
    @Column(name = "updated_on", nullable = false, columnDefinition = "TIMESTAMP")
    protected LocalDateTime lastUpdated;

    @JsonIgnore
    @Column(name = "active", columnDefinition = "INT default 1")
    protected boolean active = true;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

}