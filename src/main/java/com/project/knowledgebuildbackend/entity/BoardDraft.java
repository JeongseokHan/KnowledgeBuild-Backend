package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "board_draft")
public class BoardDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_draft_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long boardDraftId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_id", nullable = false)
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", columnDefinition = "INT UNSIGNED DEFAULT NULL")
    private Category category;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "thumbnail_path", length = 50)
    private String thumbnailPath;

    @Column(name = "thumbnail_type", length = 20)
    private String thumbnailType;

    @Column(name = "thumbnail_size", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long thumbnailSize;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
