package com.game.core.comment.application;

import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.comment.domain.Comment;
import com.game.core.comment.domain.LikeComment;
import com.game.core.comment.domain.convert.CommentConverter;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.request.UpdateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentResponse;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    private final LikeCommentRepository likeCommentRepository;
    private final CommentConverter converter;


    @Transactional
    public void createComment(Long id,LoggedInMember loggedInMember, CreateCommentRequest createCommentRequest){
        boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        Comment A =   converter.convertToCommentEntity(createCommentRequest, loggedInMember, id);
         commentRepository.save(A);
    }

    @Transactional
    public void updateComment(Long id, UpdateCommentRequest updateCommentRequest, LoggedInMember member){
        Comment comment = commentRepository.findCommentByIdAndUserId(id, member.getId())
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
            comment.update(updateCommentRequest.getContent());
            commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Page<ReadCommentResponse> getComments(Long boardId, LoggedInMember loggedInMember, Pageable pageable) {
        Optional<LikeComment> likeComment = likeCommentRepository.findByUserId(loggedInMember.getId());
        Page<Comment> comments = commentRepository.findAllByBoardId(boardId, pageable);
        return comments.map(
            Comment -> converter.convertToReadCommentResponse(Comment, loggedInMember, likeComment)
        );
    }

    @Transactional
    public void deleteComment(Long commentId, LoggedInMember loggedInMember) {
        commentRepository.deleteByIdAndUserId(commentId, loggedInMember.getId());
    }
}