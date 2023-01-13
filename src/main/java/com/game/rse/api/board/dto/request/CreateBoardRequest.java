package com.game.rse.api.board.dto.request;

import com.game.rse.api.board.domain.vo.LikeTag;
import com.game.rse.api.board.domain.vo.Type;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoardRequest {
    private Long id;
    private String nickName;
    private String title;
    private String content;
    private Type type;
    private LocalDateTime createTime;
    private LocalDateTime modified;
    private LikeTag like;
    private Long views;
}