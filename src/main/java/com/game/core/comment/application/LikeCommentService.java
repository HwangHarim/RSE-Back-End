package com.game.core.comment.application;

import com.game.core.comment.domain.Comment;
import com.game.core.comment.domain.LikeComment;
import com.game.core.comment.domain.convert.CommentConverter;
import com.game.core.comment.dto.response.ReadLikeCommentResponse;
import com.game.core.comment.infrastructure.CommentRepository;
import com.game.core.comment.infrastructure.LikeCommentRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.member.dto.LoggedInMember;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;
    private final CommentConverter converter;

    @Transactional
    public void upLikeViews(Long id, LoggedInMember member){
        Optional<LikeComment> likeComment = likeCommentRepository.findByUserId(member.getId());
        if(likeComment.isEmpty()){
            likeComment = Optional.of(new LikeComment(member.getId()));
        }
        Comment comment = commentRepository.findCommentByIdAndUserId(id, member.getId())
            .orElseThrow(() -> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD));
        if(likeComment.get().getLikeCommentIds().contains(id)){
            return;
        }
        likeComment.get().updateLikeComment(comment.getId());
        comment.upLikeView(comment.getLikeViews());
        likeCommentRepository.save(likeComment.get());
    }

    @Transactional
    public void downLikeViews(Long id, LoggedInMember member){
        LikeComment likeComment = likeCommentRepository.findByUserId(member.getId())
            .orElseThrow(
                () -> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD)
            );
        Comment comment = commentRepository.findById(id)
            .orElseThrow(
                () -> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD)
            );
        comment.downLikeView(comment.getLikeViews());
        likeComment.updateUnlikeComment(id);
        likeCommentRepository.save(likeComment);
    }

    @Transactional(readOnly = true)
    public Page<ReadLikeCommentResponse> getAllLikeComments(LoggedInMember member, Pageable pageable){
        Optional<LikeComment> likeComment = likeCommentRepository.findByUserId(member.getId());
        if(likeComment.isEmpty()) return Page.empty();
        Page<Comment> comments = commentRepository.findByIdIn(likeComment.get().getLikeCommentIds(), pageable);
        return comments.map(
            Comment -> converter.convertToReadLikeCommentResponse(Comment, member)
        );
    }
}