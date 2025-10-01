package com.jaberrantisi.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;


@Entity @Table(name = "tf_files")
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class TfFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column(name = "s3_key", nullable = false, length = Integer.MAX_VALUE)
    private String s3Key;

    @Column(name = "size")
    private Long size;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

}