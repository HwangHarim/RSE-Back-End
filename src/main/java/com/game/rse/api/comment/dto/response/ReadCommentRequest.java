package com.game.rse.api.comment.dto.response;

import com.game.rse.api.board.domain.vo.LikeTag;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadCommentRequest {
    Long boardId;
    String comment;
    LikeTag likeTag;
}
