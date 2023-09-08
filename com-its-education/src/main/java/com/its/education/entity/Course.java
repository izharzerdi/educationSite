package com.its.education.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "courses")
@Setter
@Getter
@NoArgsConstructor
public class Course implements Serializable {

    private static final long serialVersionUID = 2083705363728988301L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String langCode;
    private String langName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        this.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
    }

}
