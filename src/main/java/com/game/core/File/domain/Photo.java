package com.game.core.File.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.game.core.board.domain.Board;
import com.game.core.common.domain.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Photo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String fileName;

    private Long fileSize;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Photo(String fileName, String filePath, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public void setBoard(Board board) {
        if (this.board != null) {
            this.board.getPhotos().remove(this);
        }
        this.board = board;
        //무한 루프 빠지지 않도록
        if (!board.getPhotos().contains(this))
            board.getPhotos().add(this);
    }
}
