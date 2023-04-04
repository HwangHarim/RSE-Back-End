package com.game.core.comment.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.LikeTag;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.request.UpdateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentRequest;
import com.game.core.comment.infrastructure.CommentRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.comment.domain.Comment;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.member.dto.LoggedInMember;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createComment(Long id, CreateCommentRequest createCommentRequest){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.addComment(commentRepository.save(
            Comment.builder()
                .content(createCommentRequest.getContent())
                .likeViews(board.getLikeCount())
                .board(board)
                .build()
        ));
        boardRepository.save(board);
    }

    @Transactional
    public void updateComment(Long id, UpdateCommentRequest updateCommentRequest, LoggedInMember member){
        Comment comment = commentRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        if(Objects.equals(comment.getBoard().getUserName(), member.getId())){
            comment.update(
                updateCommentRequest.getContent()
            );
            commentRepository.save(comment);
            return;
        }
        throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
    }

    @Transactional
    public void upLikeViews(Long id, LoggedInMember member){
        Comment comment = commentRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        if(Objects.equals(comment.getBoard().getUserName(), member.getId())){
            comment.upLikeView(comment.getLikeViews());
            commentRepository.save(comment);
            return;
        }
        throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
    }

    @Transactional
    public void downLikeViews(Long id, LoggedInMember member){
        Comment comment = commentRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        if(Objects.equals(comment.getBoard().getUserName(), member.getId())){
            comment.downLikeView(comment.getLikeViews());
            commentRepository.save(comment);
            return;
        }
        throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
    }


    @Transactional
    public List<ReadCommentRequest> getComments(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        List<ReadCommentRequest> commentRequests = new ArrayList<>();

        comments.forEach(s -> commentRequests.add(
            ReadCommentRequest.builder()
                .id(s.getId())
                .boardId(s.getBoard().getId())
                .comment(s.getContent())
                .likeView(s.getLikeViews())
                .build()
        ));
    return commentRequests;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(()-> {
            throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
        });
        commentRepository.deleteById(comment.getId());
    }
}