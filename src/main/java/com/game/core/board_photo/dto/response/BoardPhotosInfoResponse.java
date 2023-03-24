package com.game.core.board_photo.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardPhotosInfoResponse {

    private final Long id;
    private final String image;

}