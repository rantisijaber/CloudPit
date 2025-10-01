package com.jaberrantisi.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity @Table(name = "users")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "sub", nullable = false)
    private String sub;

    @Column(name = "email", nullable = false)
    private String email;

    @ColumnDefault("false")
    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;

    @Column(name = "name")
    private String name;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "createdBy")
    private Set<S3Bucket> s3Buckets = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "team_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    private Set<TfFile> tfFiles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "createdBy")
    @JsonManagedReference
    private Set<Team> teamsCreated = new LinkedHashSet<>();

}
