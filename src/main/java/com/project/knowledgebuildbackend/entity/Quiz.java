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
@Table(name = "quiz")
@DynamicInsert
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long quizId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_id", nullable = false)
    private User uploader;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "quiz_introduction", nullable = false)
    private String quizIntroduction;

    @Column(name = "view_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long viewCount;

    @Column(name = "comment_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long commentCount;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long likeCount;

    @Column(name = "question_count", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long questionCount;

    @Column(name = "is_public", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPublic;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
