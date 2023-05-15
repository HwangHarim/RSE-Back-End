package com.game.core.board.application;

import static org.springframework.data.jpa.domain.Specification.where;

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
            Board ->
                ReadBoardResponse.builder()
                    .id(Board.getId())
                    .userName(Board.getUserName())
                    .title(Board.getTitle())
                    .content(Board.getContent())
                    .createTime(Board.getCreatedDate())
                    .view(Board.getView())
                    .likeCount(Board.getLikeCount())
                    .type(String.valueOf(Board.getType()))
                    .modified(Board.getModifiedDate())
                    .build()
        );

    }

    @Transactional
    public Page<ReadBoardResponse> getTypeBoards(Pageable pageable, Type type){
        Page<Board> boardPage = boardRepository.findAll(
            where(BoardSpecification.searchLecture(type))
            , pageable);
        return boardPage.map(
            Board ->
                ReadBoardResponse.builder()
                    .id(Board.getId())
                    .userName(Board.getUserName())
                    .title(Board.getTitle())
                    .content(Board.getContent())
                    .view(Board.getView())
                    .likeCount(Board.getLikeCount())
                    .createTime(Board.getCreatedDate())
                    .type(String.valueOf(Board.getType()))
                    .modified(Board.getModifiedDate())
                    .build()
        );

    }

    @Transactional
    public Page<ReadBoardResponse> getTitleBoards(Pageable pageable, String title){
        Page<Board> boardPage = boardRepository.findAll(
            where(BoardSpecification.searchTitleLecture(title))
            , pageable);
        return boardPage.map(
            Board ->
                ReadBoardResponse.builder()
                    .id(Board.getId())
                    .userName(Board.getUserName())
                    .title(Board.getTitle())
                    .content(Board.getContent())
                    .createTime(Board.getCreatedDate())
                    .type(String.valueOf(Board.getType()))
                    .view(Board.getView())
                    .likeCount(Board.getLikeCount())
                    .modified(Board.getModifiedDate())
                    .build()
        );

    }

    @Transactional
    public void createBoard(@NotNull CreateBoardRequest createBoardRequest, String user) {
        Board board = Board.builder()
            .userName(user)
            .title(createBoardRequest.getTitle())
            .content(createBoardRequest.getContent())
            .type(Type.valueOf(createBoardRequest.getType()))
            .build();
        boardRepository.save(board);
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
            boardRepository.save(board);
        }

    }

    @Transactional
    public ReadBoardResponse findBoard(Long id, LoggedInMember loggedInMember) {
        Optional<Board> board = boardRepository.findById(id);
        boolean mineStatus = false;
        if(Objects.equals(loggedInMember.getId(), board.get().getUserName())){
            mineStatus = true;
        }
        board.get().updateView(board.get().getView());
        return ReadBoardResponse.builder()
            .id(board.get().getId())
            .title(board.get().getTitle())
            .userName(board.get().getUserName())
            .content(board.get().getContent())
            .type(board.get().getType().name())
            .view(board.get().getView())
            .likeCount(board.get().getLikeCount())
            .createTime(board.get().getCreatedDate())
            .modified(board.get().getModifiedDate())
            .mine(mineStatus)
            .build();
    }

    @Transactional
    public void deleteBoard(Long id, LoggedInMember loggedInMember){
        Optional<Board> board = boardRepository.findById(id);
        if(Objects.equals(loggedInMember.getId(), board.get().getUserName())){
            boardRepository.deleteById(id);
        }
    }
}