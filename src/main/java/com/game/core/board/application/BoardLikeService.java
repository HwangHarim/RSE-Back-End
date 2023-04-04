package com.game.core.board.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.BoardLike;
import com.game.core.board.dto.response.boardLike.ReadBoardLikeResponse;
import com.game.core.board.infrastructure.BoardLikeRepository;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.board.NotFindBoardException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
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
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.updateUpLikeCount(board.getLikeCount());
        boardLikeRepository.save(new BoardLike(memberId, board));
    }

    public void unLikeBoard(Long boardId, String memberId){
        Optional<BoardLike> board = Optional.ofNullable(
            boardLikeRepository.findByBoardIdAndMemberId(boardId, memberId)
                .orElseThrow(() -> {
                    throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
                }));
       boardLikeRepository.deleteById(board.get().getId());
       Optional<Board> inBoard = boardRepository.findById(boardId);
       inBoard.get().updateDownLikeCount(inBoard.get().getLikeCount());
       boardRepository.save(inBoard.get());
    }

    public Stream<ReadBoardLikeResponse> likeBoards(String userId){
        List<BoardLike> likeBoards = boardLikeRepository.findByMemberId(userId);
        return likeBoards.stream().map(
            BoardLike  ->
                ReadBoardLikeResponse.builder()
                    .id(BoardLike.getBoard().getId())
                    .userName(BoardLike.getBoard().getUserName())
                    .title(BoardLike.getBoard().getTitle())
                    .content(BoardLike.getBoard().getContent())
                    .views(BoardLike.getBoard().getView())
                    .likeCount(BoardLike.getBoard().getLikeCount())
                    .createTime(BoardLike.getBoard().getCreatedDate())
                    .modified(BoardLike.getBoard().getModifiedDate())
                    .build()
        );
    }
}