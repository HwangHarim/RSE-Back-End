package com.game.core.board.application;

import com.game.core.board.domain.Board;
import com.game.core.board.dto.response.boardLike.ReadBoardLikeResponse;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.member.domain.User;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public void likeBoard(Long boardId, LoggedInMember member){
        Board board = boardRepository.findById(boardId)
            .orElseThrow(()-> {
                throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
            });
        board.updateUpLikeCount(board.getLikeCount());
        User user = userRepository.findByUserId(member.getId());
        if(!user.getLikeIds().contains(boardId)){
            user.getLikeIds().add(boardId);
            userRepository.save(user);
        }
    }

    public void unLikeBoard(Long boardId, LoggedInMember member){
        User user = userRepository.findByUserId(member.getId());
        if(!user.getLikeIds().contains(boardId)){
            throw new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD);
        }
        user.getLikeIds().remove(boardId);
        Optional<Board> inBoard = boardRepository.findById(boardId);
        inBoard.get().updateDownLikeCount(inBoard.get().getLikeCount());
        userRepository.save(user);
        boardRepository.save(inBoard.get());
    }

    public Stream<ReadBoardLikeResponse> likeBoards(LoggedInMember member){
        User user = userRepository.findByUserId(member.getId());
        List<Board> likes = new ArrayList<>();
        for(long boardId : user.getLikeIds()){
            likes.add(boardRepository.findById(boardId).get());
        }
        return likes.stream().map(
            Board ->
                ReadBoardLikeResponse.builder()
                    .id(Board.getId())
                    .userName(Board.getUserName())
                    .title(Board.getTitle())
                    .content(Board.getContent())
                    .views(Board.getView())
                    .likeCount(Board.getLikeCount())
                    .createTime(Board.getCreatedDate())
                    .modified(Board.getModifiedDate())
                    .build()
        );
    }
}