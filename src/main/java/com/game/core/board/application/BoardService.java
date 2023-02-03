package com.game.core.board.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.Type;
import com.game.core.board.dto.response.ReadBoardResponse;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.board.dto.request.CreateBoardRequest;
import com.game.core.board.dto.request.UpdateBoardRequest;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.NullPointerException;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Page<ReadBoardResponse> getBoards(Pageable pageable){
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(
            Board ->
                ReadBoardResponse.builder()
                    .id(Board.getId())
                    .userName(Board.getUserName())
                    .title(Board.getTitle())
                    .content(Board.getContent())
                    .createTime(Board.getCreatedDate())
                    .modified(Board.getModifiedDate())
                    .build()
        );
    }

    @Transactional
    public void createBoard(CreateBoardRequest createBoardRequest, String userName) {
        Board board = Board.builder()
            .userName(userName)
            .title(createBoardRequest.getTitle())
            .content(createBoardRequest.getContent())
            .type(Type.valueOf(createBoardRequest.getType()))
            .build();
        boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(Long id,UpdateBoardRequest updateBoardRequest){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
                });
        board.update(
           updateBoardRequest.getTitle(),
            updateBoardRequest.getContent()
        );
        boardRepository.save(board);
    }

    public ReadBoardResponse findBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return ReadBoardResponse.builder()
            .id(board.get().getId())
            .title(board.get().getTitle())
            .userName(board.get().getUserName())
            .content(board.get().getContent())
            .type(board.get().getType().name())
            .createTime(board.get().getCreatedDate())
            .modified(board.get().getModifiedDate())
            .build();
    }



    public void changeTag(Long id, String tag){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.changeTage(Type.valueOf(tag));
        boardRepository.save(board);
    }

    @Transactional
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }

    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }
}