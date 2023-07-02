package com.game.core.board.dto.request.boardLike;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateLikeBoardRequest {
    private String member_id;
    private Long boardId;
}