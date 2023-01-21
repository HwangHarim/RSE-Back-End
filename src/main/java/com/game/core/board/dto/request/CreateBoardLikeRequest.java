package com.game.core.board.dto.request;

import com.game.core.board.domain.Board;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBoardLikeRequest {
    private long member_id;
    private Board board;
}
