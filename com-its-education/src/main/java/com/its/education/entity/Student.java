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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "students")
@Setter
@Getter
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String emailAddress;
    private String telephoneNumber;
    private String address;
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
