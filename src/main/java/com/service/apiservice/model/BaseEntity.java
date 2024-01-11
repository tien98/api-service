package com.service.apiservice.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {
    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "LASTUPDATEDBY")
    private String lastUpdatedBy;

    @Column(name = "CREATEDAT")
    private Timestamp createdAt;

    @Column(name = "LASTUPDATEDAT")
    private Timestamp lastUpdatedAt;

    @PrePersist
    protected void prePersist() {
        //Calendar cal = Calendar.getInstance();
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        if (this.createdAt == null) createdAt = Timestamp.valueOf(LocalDateTime.now(zoneId));
        if (this.lastUpdatedAt == null) lastUpdatedAt = Timestamp.valueOf(LocalDateTime.now(zoneId));
    }

    @PreUpdate
    protected void preUpdate() {
        //Calendar cal = Calendar.getInstance();
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        this.lastUpdatedAt = Timestamp.valueOf(LocalDateTime.now(zoneId));
    }

    @PreRemove
    protected void preRemove() {
        //Calendar cal = Calendar.getInstance();
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        this.lastUpdatedAt = Timestamp.valueOf(LocalDateTime.now(zoneId));
    }
}
