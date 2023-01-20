package com.game.core.comment.dto.response;

import com.game.core.board.domain.vo.LikeTag;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadCommentRequest {
    Long boardId;
    String comment;
    LikeTag likeTag;
}
