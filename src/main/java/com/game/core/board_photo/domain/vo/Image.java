package com.game.core.board_photo.domain.vo;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.image.InvalidArgumentException;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.StringUtils;

@Embeddable
public class Image {

    @Column(name = "board_photo_image")
    private String image;

    protected Image() {
    }

    public Image(String image) {
        validate(image);
        this.image = image;
    }

    private void validate(String image) {
        if (!StringUtils.hasText(image)) {
            throw new InvalidArgumentException(ErrorMessage.INVALID_BOARD_PHOTO_IMAGE);
        }
    }

    public String getImage() {
        return image;
    }
}
