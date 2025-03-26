package com.project.knowledgebuildbackend.entity;

import com.project.knowledgebuildbackend.entity.composite.BoardBookmarkPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "board_bookmark")
@DynamicInsert
public class BoardBookmark {
    @EmbeddedId
    private BoardBookmarkPK boardBookmarkPK;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
}
