package com.game.core.board.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.BoardLike;
import com.game.core.board.infrastructure.BoardLikeRepository;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.NullPointerException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    public void likeBoard(Long boardId, String memberId){
        Board board = boardRepository.findById(boardId)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        boardLikeRepository.save(new BoardLike(memberId, board));
    }

    public void unLikeBoard(Long boardId, String memberId){
        Board board = boardRepository.findById(boardId)
            .orElseThrow(()-> {
                throw new NullPointerException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        boardLikeRepository.deleteByBoardIdAndMemberId(boardId, memberId);
    }

    public List<Board> likeBoards(String userId){
        List<BoardLike> likeBoards = boardLikeRepository.findByMemberId(userId);
        List<Board> likeBoardAll = new ArrayList<>();
        for (BoardLike board : likeBoards) {
          likeBoardAll.add(board.getBoard());
        }
        return likeBoardAll;
    }
}