package com.game.core.comment.application;

import com.game.core.board.domain.Board;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.request.UpdateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentResponse;
import com.game.core.comment.infrastructure.CommentRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.comment.domain.Comment;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.UserRepository;
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
    public void createComment(Long id,LoggedInMember loggedInMember, CreateCommentRequest createCommentRequest){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.addComment(commentRepository.save(
            Comment.builder()
                .content(createCommentRequest.getContent())
                .userId(loggedInMember.getId())
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
        if(Objects.equals(comment.getBoard().getUserName(), member.getUserName())){
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
        if(Objects.equals(comment.getBoard().getUserName(), member.getUserName())){
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
        if(Objects.equals(comment.getBoard().getUserName(), member.getUserName())){
            comment.downLikeView(comment.getLikeViews());
            commentRepository.save(comment);
            return;
        }
        throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
    }


    @Transactional
    public List<ReadCommentResponse> getComments(Long boardId, LoggedInMember loggedInMember) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        List<ReadCommentResponse> commentRequests = new ArrayList<>();
        List<ReadCommentResponse> commentResults = new ArrayList<>();


        comments.forEach(s -> commentRequests.add(
            ReadCommentResponse.builder()
                .id(s.getId())
                .boardId(s.getBoard().getId())
                .userId(loggedInMember.getId())
                .userName(loggedInMember.getUserName())
                .comment(s.getContent())
                .mine(false)
                .likeView(s.getLikeViews())
                .build()
        ));
        for(ReadCommentResponse x : commentRequests){
            if(loggedInMember.getId().equals(x.getUserId())){
                x.setMine(true);
                commentResults.add(x);
            }else{
                commentResults.add(x);
            }
        }

        return commentResults;
    }

    @Transactional
    public void deleteComment(Long commentId, LoggedInMember loggedInMember) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        if(Objects.equals(loggedInMember.getUserName(), comment.getBoard().getUserName())){
            commentRepository.deleteById(comment.getId());
        }
    }
}