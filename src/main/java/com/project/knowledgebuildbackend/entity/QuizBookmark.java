package com.project.knowledgebuildbackend.entity;

import com.project.knowledgebuildbackend.entity.composite.QuizActivityPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "quiz_bookmark")
public class QuizBookmark {
    @EmbeddedId
    private QuizActivityPK quizActivityPK;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
}
