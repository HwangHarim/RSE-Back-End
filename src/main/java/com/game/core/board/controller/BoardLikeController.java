package com.game.core.board.controller;

import com.game.core.board.application.BoardLikeService;
import com.game.core.board.dto.response.board.ReadAllBoardResponse;
import com.game.core.common.response.dto.ResponseDto;
import com.game.core.common.response.dto.ResponseMessage;
import com.game.core.common.response.handler.ResponseHandler;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.annotation.AuthMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("board")
@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;
    private final ResponseHandler responseHandler;

    @ApiOperation("board 즐겨 찾기")
    @PostMapping(value = "/{id}/likes")
    public ResponseEntity<ResponseDto> updateLikeBoard(
        @AuthMember LoggedInMember loggedInMember,
        @PathVariable("id") Long id){
        boardLikeService.likeBoard(id, loggedInMember);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like board"
        );
    }

    @ApiOperation("board 즐겨 찾기 해제")
    @PatchMapping(value = "/{id}/unlikes")
    public ResponseEntity<ResponseDto> updateUnLikeBoard(
        @AuthMember LoggedInMember loggedInMember,
        @PathVariable("id") Long id){
        boardLikeService.unLikeBoard(id, loggedInMember);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "unLike board"
        );
    }

    @ApiOperation("모든 board 즐겨 찾기")
    @GetMapping(value = "/likes")
    public ResponseEntity<ResponseDto> allLikeBoard(
        @AuthMember LoggedInMember loggedInMember,
        @PageableDefault(sort ="id", size = 15,direction= Direction.ASC)
        Pageable pageable) {
        Page<ReadAllBoardResponse> allLikeBoards = boardLikeService.likeBoards(loggedInMember, pageable);

        return responseHandler.toResponseEntity(
            ResponseMessage.CREATE_BOARD_SUCCESS,
            allLikeBoards
        );
    }

    @ApiOperation("모든 board 즐겨 찾기")
    @GetMapping(value = "{boardId}/likes/counts")
    public ResponseEntity<ResponseDto> allLikeBoard(
        @PathVariable Long boardId) {
        Long likeCount = boardLikeService.totalLikeCount(boardId);
        return responseHandler.toResponseEntity(
            ResponseMessage.READ_VIEW_BOARD_SUCCESS,
            likeCount
        );
    }
}