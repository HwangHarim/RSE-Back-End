package com.game.core.comment.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.LikeTag;
import com.game.core.board.dto.request.UpdateBoardRequest;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.request.UpdateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentRequest;
import com.game.core.comment.infrastructure.CommentRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.NullPointerException;
import com.game.core.comment.domain.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void createComment(Long id, CreateCommentRequest createCommentRequest){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.addComment(commentRepository.save(
            Comment.builder()
                .content(createCommentRequest.getContent())
                .likeTag(LikeTag.NORMAL)
                .board(board)
                .build()
        ));
        boardRepository.save(board);
    }

    @Transactional
    public void updateComment(Long id, UpdateCommentRequest updateCommentRequest){
        Comment comment = commentRepository.findById(id)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        comment.update(
            updateCommentRequest.getContent()
        );
        commentRepository.save(comment);
    }

    public List<ReadCommentRequest> getComments(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        List<ReadCommentRequest> commentRequests = new ArrayList<>();

        comments.forEach(s -> commentRequests.add(
            ReadCommentRequest.builder()
                .boardId(s.getBoard().getId())
                .comment(s.getContent())
                .likeTag(s.getLikeTag())
                .build()
        ));
    return commentRequests;
    }


        @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(()-> {
            throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
        });
        commentRepository.deleteById(commentId);
    }
}