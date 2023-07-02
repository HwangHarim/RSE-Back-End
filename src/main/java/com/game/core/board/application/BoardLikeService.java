package com.game.core.board.application;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.LikeBoard;
import com.game.core.board.domain.vo.convert.BoardConverter;
import com.game.core.board.dto.response.board.ReadAllBoardResponse;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.board.infrastructure.LikeBoardRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.board.NotFindBoardException;
import com.game.core.error.exception.member.NotFoundUserException;
import com.game.core.member.dto.LoggedInMember;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {
    private final LikeBoardRepository likeBoardRepository;
    private final BoardRepository boardRepository;
    private final BoardConverter converter;

    @Transactional
    public void likeBoard(Long boardId, LoggedInMember member){
        Optional<LikeBoard> likeBoard = likeBoardRepository.findByUserId(member.getId());
        if(likeBoard.isEmpty()){
             likeBoard = Optional.of(new LikeBoard(member.getId()));
        }
        Board board = boardRepository.findById(boardId).orElseThrow(
            () -> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD)
        );
        if(likeBoard.get().getLikeIds().contains(boardId)){
            return;
        }
        board.updateUpLikeCount(board.getLikeCount());
        likeBoard.get().updateLikeBoard(boardId);
        likeBoardRepository.save(likeBoard.get());
    }

    @Transactional
    public void unLikeBoard(Long boardId, LoggedInMember member){
        LikeBoard likeBoard = likeBoardRepository.findByUserId(member.getId())
            .orElseThrow(()-> new NotFoundUserException(ErrorMessage.NOT_FIND_USER_MESSAGE));
        Board board = boardRepository.findById(boardId)
            .orElseThrow(
            () -> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD)
        );
        board.updateDownLikeCount(board.getLikeCount());
        likeBoard.updateUnlikeBoard(boardId);
        likeBoardRepository.save(likeBoard);
    }

    @Transactional(readOnly = true)
    public Long totalLikeCount(Long boardId){
        return boardRepository.findById(boardId)
            .orElseThrow(
                ()-> new NotFindBoardException(ErrorMessage.NOT_FIND_ID_BOARD))
            .getLikeCount();
    }

    @Transactional(readOnly = true)
    public Page<ReadAllBoardResponse> likeBoards(LoggedInMember member, Pageable pageable){
        Optional<LikeBoard> likeBoard = likeBoardRepository.findByUserId(member.getId());
        if(likeBoard.isEmpty()){
            return Page.empty();
        }
        Page<Board> likeBoards = boardRepository.findByIdIn(likeBoard.get().getLikeIds(), pageable);
        return likeBoards.map(converter::convertToReadAllBoardResponse);
    }
}