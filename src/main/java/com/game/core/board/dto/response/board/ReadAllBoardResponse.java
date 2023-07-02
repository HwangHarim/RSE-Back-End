package com.game.core.board.dto.response.board;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadAllBoardResponse {
    private Long id;
    private String userName;
    private String title;
    private String content;
    private String type;
    private LocalDateTime createTime;
    private LocalDateTime modified;
}
