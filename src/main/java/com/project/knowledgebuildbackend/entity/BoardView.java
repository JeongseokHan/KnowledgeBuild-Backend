package com.project.knowledgebuildbackend.entity;

import com.project.knowledgebuildbackend.entity.composite.BoardViewPK;
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
@Table(name = "board_view")
@DynamicInsert
public class BoardView {
    @EmbeddedId
    private BoardViewPK boardViewPK;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTime;
}
