package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "quiz_question_draft")
public class QuizQuestionDraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_draft_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long questionDraftId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_draft_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private QuizDraft quizDraft;

    @Column(name = "question_num", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long questionNum;

    @Column(name = "question_text")
    private String questionText;
}
