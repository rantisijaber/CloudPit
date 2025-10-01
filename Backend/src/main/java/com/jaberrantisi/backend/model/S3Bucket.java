package com.jaberrantisi.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;


@Entity @Table(name = "s3_buckets")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class S3Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "url", nullable = false, length = Integer.MAX_VALUE)
    private String url;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

}