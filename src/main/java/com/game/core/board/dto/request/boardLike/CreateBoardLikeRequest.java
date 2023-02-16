package com.game.core.board.dto.request.boardLike;

import com.game.core.board.domain.Board;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBoardLikeRequest {
    private String member_id;
    private Board board;
}