package com.game.core.board.application;

import static org.springframework.data.jpa.domain.Specification.where;

import com.game.core.board.converter.BoardConverter;
import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.Type;
import com.game.core.board.dto.response.board.ReadBoardResponse;
import com.game.core.board.filter.BoardSpecification;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.board.dto.request.board.CreateBoardRequest;
import com.game.core.board.dto.request.board.UpdateBoardRequest;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.member.dto.LoggedInMember;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Page<ReadBoardResponse> getAllBoards(Pageable pageable){
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(
            BoardConverter::toAllReadBoardResponse
        );

    }

    @Transactional
    public Page<ReadBoardResponse> getTypeBoards(Pageable pageable, Type type){
        Page<Board> boardPage = boardRepository.findAll(
            where(BoardSpecification.searchLecture(type))
            , pageable);
        return boardPage.map(
            BoardConverter::toAllReadBoardResponse
        );

    }

    @Transactional
    public Page<ReadBoardResponse> getTitleBoards(Pageable pageable, String title){
        Page<Board> boardPage = boardRepository.findAll(
            where(BoardSpecification.searchTitleLecture(title))
            , pageable);
        return boardPage.map(
                BoardConverter::toAllReadBoardResponse
        );
    }

    @Transactional
    public void createBoard(@NotNull CreateBoardRequest createBoardRequest, String user) {
        boardRepository.save(BoardConverter.toBoardEntity(createBoardRequest, user));
    }

    @Transactional
    public void updateBoard(Long id, LoggedInMember loggedInMember, UpdateBoardRequest updateBoardRequest){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        if(Objects.equals(loggedInMember.getId(), board.getUserName())){
            board.update(
                updateBoardRequest.getTitle(),
                updateBoardRequest.getContent()
            );
        }
    }

    @Transactional
    public ReadBoardResponse findBoard(Long id, LoggedInMember loggedInMember) {
        return BoardConverter.toReadBoardResponse(boardRepository.findById(id).get(), loggedInMember);
    }

    @Transactional
    public void deleteBoard(Long id, LoggedInMember loggedInMember){
        Optional<Board> board = boardRepository.findById(id);
        if(Objects.equals(loggedInMember.getId(), board.get().getUserName())){
            boardRepository.deleteById(id);
        }
    }
}