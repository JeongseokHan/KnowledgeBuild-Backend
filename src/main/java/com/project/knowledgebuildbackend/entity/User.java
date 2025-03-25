package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user")
@DynamicInsert
public class User {
    @Id
    @Column(name = "sns_id", unique = true, nullable = false)
    private String snsId;

    /**
     * 1001 : 카카오톡
     * 1002 : 네이버
     * 1003 : 구글
     */
    @Column(name = "sns_kind", nullable = false, columnDefinition = "CHAR", length = 4)
    private String snsKind;

    @Column(name = "nickname", unique = true, nullable = false, length = 15)
    private String nickname;

    @Column(name = "description", length = 50)
    private String description; // 자기소개

    @Column(name = "profile_path", length = 50)
    private String profilePath;

    @Column(name = "profile_type", length = 20)
    private String profileType;

    @Column(name = "profile_size", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long profileSize;

    @Column(name = "quiz_count", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long quizCount;

    @Column(name = "board_count", columnDefinition = "INT UNSIGNED DEFAULT 0")
    private Long boardCount;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
}
