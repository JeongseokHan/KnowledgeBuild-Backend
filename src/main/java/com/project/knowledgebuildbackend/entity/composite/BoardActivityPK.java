package com.project.knowledgebuildbackend.entity.composite;

import com.project.knowledgebuildbackend.entity.Board;
import com.project.knowledgebuildbackend.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class BoardActivityPK implements Serializable {
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sns_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Board board;
}