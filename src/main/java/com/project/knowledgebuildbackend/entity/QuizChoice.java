package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "quiz_choice")
public class QuizChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long choiceId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private QuizQuestion quizQuestion;

    @Column(name = "choice_text", nullable = false)
    private String choiceText;

    @Column(name = "is_correct", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCorrect;
}
