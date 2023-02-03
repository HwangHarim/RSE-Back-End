package com.game.core.comment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadCommentRequest {
    Long boardId;
    String comment;
    LikeTag likeTag;
}
