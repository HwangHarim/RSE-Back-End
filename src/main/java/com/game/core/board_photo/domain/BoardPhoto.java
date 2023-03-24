package com.game.core.board_photo.domain;

import com.game.core.board.domain.Board;
import com.game.core.board_photo.domain.vo.Image;
import com.game.core.common.domain.BaseTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class BoardPhoto extends BaseTime {

    @Id
    @Column(name = "board_photo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardPhoto(Image image) {
        this.image = image;
    }

    public void register(Board board) {
        this.board = board;
    }

    public String getImageValue(){
        return image.getImage();
    }
}