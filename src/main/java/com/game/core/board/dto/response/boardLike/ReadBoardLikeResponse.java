package com.game.core.board.dto.response.boardLike;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadBoardLikeResponse{
    private Long id;
    private String userName;
    private String title;
    private String content;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime modified;
    private Long views;
    private Long likeCount;
}
