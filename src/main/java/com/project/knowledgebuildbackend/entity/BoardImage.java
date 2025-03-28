package com.project.knowledgebuildbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "board_image")
@DynamicInsert
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_image_id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED AUTO_INCREMENT")
    private Long boardImageId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", columnDefinition = "INT UNSIGNED DEFAULT NULL")
    private Board board;

    @Column(name = "board_image_path", nullable = false, length = 50)
    private String boardImagePath;

    @Column(name = "board_image_type", nullable = false, length = 20)
    private String boardImageType;

    @Column(name = "board_image_size", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long boardImageSize;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_draft_id", columnDefinition = "INT UNSIGNED DEFAULT NULL")
    private BoardDraft boardDraft;

}
