package com.game.core.board.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.LikeTag;
import com.game.core.board.domain.vo.Type;
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
    public Page<CreateBoardRequest> getBoards(Pageable pageable){
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(
            Board ->
                CreateBoardRequest.builder()
                    .id(Board.getId())
                    .nickName(Board.getNickName())
                    .title(Board.getTitle())
                    .content(Board.getContent())
                    .createTime(Board.getCreatedDate())
                    .modified(Board.getModifiedDate())
                    .build()
        );
    }

    @Transactional
    public void createBoard(CreateBoardRequest createBoardRequest) {
        Board board = Board.builder()
            .nickName(createBoardRequest.getNickName())
            .title(createBoardRequest.getTitle())
            .content(createBoardRequest.getContent())
            .type(Type.BUG)
            .likeTag(LikeTag.NORMAL)
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

    public CreateBoardRequest findBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        return CreateBoardRequest.builder()
            .id(board.get().getId())
            .title(board.get().getTitle())
            .nickName(board.get().getNickName())
            .content(board.get().getContent())
            .like(LikeTag.NORMAL)
            .type(board.get().getType())
            .createTime(board.get().getCreatedDate())
            .modified(board.get().getModifiedDate())
            .build();
    }

    public void setLike(Long id){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.setLikeTag(LikeTag.LIKE_TAG);
        boardRepository.save(board);
    }

    public void setUnLike(Long id){
        Board board = boardRepository.findById(id)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.setLikeTag(LikeTag.NORMAL);
        boardRepository.save(board);
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

//    @Transactional
//    public Long allView(Long id) {
//        return boardRepository.findById(id).get().getView();
//    }

    public void deleteBoard(Long id){
        boardRepository.deleteById(id);
    }
}