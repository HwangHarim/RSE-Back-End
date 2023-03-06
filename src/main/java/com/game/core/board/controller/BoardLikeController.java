package com.game.core.board.controller;

import com.game.core.board.application.BoardLikeService;
import com.game.core.board.domain.Board;
import com.game.core.board.dto.response.boardLike.ReadBoardLikeResponse;
import com.game.core.common.dto.Response.Handler.ResponseHandler;
import com.game.core.common.dto.Response.ResponseDto;
import com.game.core.common.dto.Response.ResponseMessage;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.annotation.AuthMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ResponseDto> updateLikeBoard(@AuthMember LoggedInMember loggedInMember, @PathVariable("id") Long id){
        boardLikeService.likeBoard(id, loggedInMember.getId());
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like board"
        );
    }

    @ApiOperation("board 즐겨 찾기 해제")
    @DeleteMapping(value = "/{id}/unlikes")
    public ResponseEntity<ResponseDto> updateUnLikeBoard(@AuthMember LoggedInMember loggedInMember,@PathVariable("id") Long id){
        boardLikeService.unLikeBoard(id, loggedInMember.getId());
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "unLike board"
        );
    }

    @ApiOperation("모든 board 즐겨 찾기")
    @GetMapping(value = "/likes")
    public ResponseEntity<ResponseDto> allLikeBoard(@AuthMember LoggedInMember loggedInMember){
       Stream<ReadBoardLikeResponse> allLikeBoards = boardLikeService.likeBoards(loggedInMember.getId());
        return responseHandler.toResponseEntity(
            ResponseMessage.CREATE_BOARD_SUCCESS,
            allLikeBoards
        );
    }
}