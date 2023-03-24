package com.game.core.board.domain.vo;

import com.game.core.board_photo.domain.BoardPhoto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class BoardPhotos {

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardPhoto> boardPhotos = new ArrayList<>();

    public void add(BoardPhoto boardPhoto) {
        boardPhotos.add(boardPhoto);
    }

    public List<String> getBoardPhotosUrl() {
        return boardPhotos.stream()
            .map(BoardPhoto::getImageValue)
            .collect(Collectors.toList());
    }

    public void removeAll() {
        boardPhotos.clear();
    }

    public String getBoardPhotoUrl() {
        return boardPhotos.stream()
            .findFirst()
            .map(BoardPhoto::getImageValue)
            .orElse(null);
    }

    public List<BoardPhoto> getBoardPhotos() {
        return boardPhotos;
    }
}
