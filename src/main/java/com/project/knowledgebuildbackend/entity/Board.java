package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "board")
@DynamicInsert
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long boardId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_id", nullable = false)
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "thumbnail_path", length = 50)
    private String thumbnailPath;

    @Column(name = "thumbnail_type", length = 20)
    private String thumbnailType;

    @Column(name = "thumbnail_size", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long thumbnailSize;

    @Column(name = "view_count", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long viewCount;

    @Column(name = "comment_count", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long commentCount;

    @Column(name = "like_count", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long likeCount;

    @Column(name = "is_public", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPublic;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;


}
