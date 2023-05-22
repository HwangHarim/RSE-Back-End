package com.game.core.board.converter;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.Type;
import com.game.core.board.dto.request.board.CreateBoardRequest;
import com.game.core.board.dto.response.board.ReadBoardResponse;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.member.dto.LoggedInMember;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardConverter {

    private final BoardRepository boardRepository;

    public static Board toBoardEntity(CreateBoardRequest createBoardRequest, String user) {
        return Board.builder()
            .userName(user)
            .title(createBoardRequest.getTitle())
            .content(createBoardRequest.getContent())
            .type(Type.valueOf(createBoardRequest.getType()))
            .build();
    }

    public static ReadBoardResponse toReadBoardResponse(Board board, LoggedInMember loggedInMember) {
        boolean mineStatus = false;
        if(Objects.equals(loggedInMember.getId(), board.getUserName())){
            mineStatus = true;
        }
        board.updateView(board.getView());
      return ReadBoardResponse.builder()
          .id(board.getId())
          .title(board.getTitle())
          .userName(board.getUserName())
          .content(board.getContent())
          .type(board.getType().name())
          .view(board.getView())
          .likeCount(board.getLikeCount())
          .createTime(board.getCreatedDate())
          .modified(board.getModifiedDate())
          .mine(mineStatus)
          .build();
    }

    public static ReadBoardResponse toAllReadBoardResponse(Board board){
        return ReadBoardResponse.builder()
            .id(board.getId())
            .userName(board.getUserName())
            .title(board.getTitle())
            .content(board.getContent())
            .createTime(board.getCreatedDate())
            .view(board.getView())
            .likeCount(board.getLikeCount())
            .type(String.valueOf(board.getType()))
            .modified(board.getModifiedDate())
            .build();
    }
}
