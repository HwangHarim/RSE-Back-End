package com.game.core.comment.convert;

import com.game.core.board.domain.Board;
import com.game.core.comment.domain.Comment;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentResponse;
import com.game.core.member.dto.LoggedInMember;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public static Comment toEntity(Board board,CreateCommentRequest createCommentRequest, LoggedInMember loggedInMember){
       return  Comment.builder()
           .content(createCommentRequest.getContent())
           .userId(loggedInMember.getId())
           .likeViews(board.getLikeCount())
           .board(board)
           .build();
    }

    public static ReadCommentResponse toReadCommentResponse(Comment comment, LoggedInMember loggedInMember) {
      return ReadCommentResponse.builder()
          .id(comment.getId())
          .boardId(comment.getBoard().getId())
          .userId(loggedInMember.getId())
          .comment(comment.getContent())
          .mine(false)
          .likeView(comment.getLikeViews())
          .build();
    }
}
