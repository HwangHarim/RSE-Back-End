package com.game.rse.api.comment.application;

import com.game.rse.api.board.domain.Board;
import com.game.rse.api.board.domain.vo.LikeTag;
import com.game.rse.api.board.infrastructure.BoardRepository;
import com.game.rse.api.comment.dto.request.CreateCommentRequest;
import com.game.rse.api.comment.dto.response.ReadCommentRequest;
import com.game.rse.api.comment.infrastructure.CommentRepository;
import com.game.rse.api.comment.domain.Comment;
import com.game.rse.util.err.ErrorMessage;
import com.game.rse.util.err.exception.NullPointerException;
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