package com.jaberrantisi.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity @Table(name = "teams")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "team")
    private Set<S3Bucket> s3Buckets = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "teams")
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "team")
    private Set<TfFile> tfFiles = new LinkedHashSet<>();

}