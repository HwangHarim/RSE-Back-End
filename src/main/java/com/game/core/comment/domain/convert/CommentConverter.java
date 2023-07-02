package com.game.core.comment.domain.convert;

import com.game.core.comment.domain.Comment;
import com.game.core.comment.domain.LikeComment;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentResponse;
import com.game.core.comment.dto.response.ReadLikeCommentResponse;
import com.game.core.comment.filter.CommentFlagFilter;
import com.game.core.member.dto.LoggedInMember;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentConverter {

    private final CommentFlagFilter filter;

    public Comment convertToCommentEntity(CreateCommentRequest request, LoggedInMember user, Long boardId) {
      return Comment.builder()
          .userId(user.getId())
          .userName(user.getUserName())
          .boardId(boardId)
          .content(request.getContent())
          .build();
    }

    public ReadCommentResponse convertToReadCommentResponse(Comment comment, LoggedInMember member, Optional<LikeComment> likeComment){
        return ReadCommentResponse.builder()
            .id(comment.getId())
            .userId(comment.getUserId())
            .userName(comment.getUserName())
            .boardId(comment.getBoardId())
            .comment(comment.getContent())
            .likeView(comment.getLikeViews())
            .mine(filter.mineFlag(comment.getUserId(), member.getId()))
            .likeFlag(filter.likeFlag(comment.getId(), likeComment))
            .build();
    }

    public ReadLikeCommentResponse convertToReadLikeCommentResponse(Comment comment, LoggedInMember member){
        return ReadLikeCommentResponse.builder()
            .id(comment.getId())
            .userId(comment.getUserId())
            .userName(comment.getUserName())
            .boardId(comment.getBoardId())
            .comment(comment.getContent())
            .mine(filter.mineFlag(comment.getUserId(), member.getId()))
            .build();
    }
}