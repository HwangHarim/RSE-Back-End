package com.game.core.board.application;

import static org.springframework.data.jpa.domain.Specification.where;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.LikeBoard;
import com.game.core.board.domain.vo.Type;
import com.game.core.board.domain.vo.convert.BoardConverter;
import com.game.core.board.dto.response.board.ReadAllBoardResponse;
import com.game.core.board.dto.response.board.ReadBoardResponse;
import com.game.core.board.filter.BoardSpecification;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.board.dto.request.board.CreateBoardRequest;
import com.game.core.board.dto.request.board.UpdateBoardRequest;
import com.game.core.board.infrastructure.LikeBoardRepository;
import com.game.core.comment.infrastructure.CommentRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.UserRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;
    private final CommentRepository commentRepository;
    private final BoardConverter converter;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<ReadAllBoardResponse> getAllBoards(Pageable pageable, String type, String title){
      if (type == null&& title== null) {
          Page<Board> boardPage = boardRepository.findAll(pageable);

          return boardPage.map(converter::convertToReadAllBoardResponse);
      }
        Page<Board> boardPage = type!=null ?
            boardRepository.findAll(where(BoardSpecification.searchLecture(Type.getType(type))), pageable) :
            boardRepository.findAll(where(BoardSpecification.searchTitleLecture(title)), pageable);

      return boardPage.map(converter::convertToReadAllBoardResponse);
    }

    @Transactional
    public void createBoard(@NotNull CreateBoardRequest createBoardRequest, LoggedInMember user) {
        boardRepository.save(converter.convertCreateBoardToBoardEntity(createBoardRequest, user));
    }

    @Transactional
    public void updateBoard(Long id, LoggedInMember loggedInMember, UpdateBoardRequest updateBoardRequest){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);});

        if(Objects.equals(loggedInMember.getId(), board.getUserId())){
            board.update(updateBoardRequest);
            boardRepository.save(board);
        }
    }

    @Transactional(readOnly = true)
    public ReadBoardResponse findBoard(Long id, LoggedInMember loggedInMember) {
        Optional<LikeBoard> likeBoard = likeBoardRepository.findByUserId(loggedInMember.getId());
        Board board = boardRepository.findById(id)
            .orElseThrow(
            () -> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD));
        userRepository.findByUserId(loggedInMember.getId());
        return converter.convertToReadBoardResponse(board, likeBoard, loggedInMember);
    }

    @Transactional
    public void deleteBoard(Long id, LoggedInMember loggedInMember){
        Board board = boardRepository.findById(id).orElseThrow(
            ()-> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD)
        );
        if(Objects.equals(loggedInMember.getId(), board.getUserId())){
            boardRepository.deleteById(id);
            commentRepository.deleteByBoardId(board.getId());
        }
    }

    @Transactional
    public Long boardView(Long id){
        Board board = boardRepository.findById(id)
            .orElseThrow(
            ()-> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD)
            );
        board.updateView(board.getView());
        boardRepository.save(board);

        return board.getView();
    }
}