package com.game.core.board.domain.vo.convert;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.LikeBoard;
import com.game.core.board.domain.vo.Type;
import com.game.core.board.dto.request.board.CreateBoardRequest;
import com.game.core.board.dto.response.board.ReadAllBoardResponse;
import com.game.core.board.dto.response.board.ReadBoardResponse;
import com.game.core.board.filter.BoardFlagFilter;
import com.game.core.member.dto.LoggedInMember;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardConverter {

    private final BoardFlagFilter filter;
    public Board convertCreateBoardToBoardEntity(CreateBoardRequest request, LoggedInMember user){
        return  Board.builder()
            .userId(user.getId())
            .userName(user.getUserName())
            .title(request.getTitle())
            .content(request.getContent())
            .type(Type.valueOf(request.getType()))
            .build();
    }

    public LikeBoard convertToLikeBoardEntity(String userId){
        return new LikeBoard(userId);
    }

    public ReadAllBoardResponse convertToReadAllBoardResponse (Board board){
        return ReadAllBoardResponse.builder()
            .id(board.getId())
            .userName(board.getUserName())
            .title(board.getTitle())
            .content(board.getContent())
            .type(String.valueOf(board.getType()))
            .viewCount(board.getView())
            .createTime(board.getCreatedDate())
            .modified(board.getModifiedDate())
            .build();
    }


    public ReadBoardResponse convertToReadBoardResponse(Board board, Optional<LikeBoard> likeBoard, LoggedInMember member) {
      return  ReadBoardResponse.builder()
          .id(board.getId())
          .userName(board.getUserName())
          .title(board.getTitle())
          .content(board.getContent())
          .type(String.valueOf(board.getType()))
          .createTime(board.getCreatedDate())
          .modified(board.getModifiedDate())
          .mine(filter.mineFlag(board.getUserId(), member.getId()))
          .likeFlag(filter.likeFlag(board.getId(), likeBoard))
          .build();
    }
}