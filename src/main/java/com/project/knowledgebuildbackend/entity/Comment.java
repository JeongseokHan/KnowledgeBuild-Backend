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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, unique = true, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long commentId;

    @Column(name = "post_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long postId; // 게시물 또는 퀴즈의 Id가 들어감

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", columnDefinition = "INT UNSIGNED DEFAULT NULL")
    private Comment parentComment;

    /**
     * 101 : 게시글
     * 102 : 퀴즈
     */
    @Column(name = "post_type", nullable = false, length = 3)
    private String postType;

    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
