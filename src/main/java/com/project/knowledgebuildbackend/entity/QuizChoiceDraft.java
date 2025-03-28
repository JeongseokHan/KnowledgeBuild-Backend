package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "quiz_choice_draft")
public class QuizChoiceDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_draft_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long choiceDraftId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_draft_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private QuizQuestionDraft quizQuestionDraft;

    @Column(name = "choice_text")
    private String choiceText;

    @Column(name = "is_correct", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isCorrect;
}
