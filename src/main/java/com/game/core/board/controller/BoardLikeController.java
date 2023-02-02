package com.game.core.board.controller;

import com.game.core.board.application.BoardLikeService;
import com.game.core.board.domain.Board;
import com.game.core.common.dto.Response.Handler.ResponseHandler;
import com.game.core.common.dto.Response.ResponseDto;
import com.game.core.common.dto.Response.ResponseMessage;
import com.game.core.member.application.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ApiResponses({
    @ApiResponse(code = 200, message = "Success"),
    @ApiResponse(code = 400, message = "Bad Request"),
    @ApiResponse(code = 500, message = "Internal Server Error")
})

@Api("board")
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;
    private final UserService userService;
    private final ResponseHandler responseHandler;

    @ApiOperation("board 즐겨 찾기")
    @PostMapping(value = "/{id}/like")
    public ResponseEntity<ResponseDto> updateLikeBoard(@PathVariable("id") Long id){
        boardLikeService.likeBoard(id, userService.getUserId());
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like board"
        );
    }

    @ApiOperation("board 즐겨 찾기 해제")
    @DeleteMapping(value = "/{id}/unlike")
    public ResponseEntity<ResponseDto> updateUnLikeBoard(@PathVariable("id") Long id){
        boardLikeService.unLikeBoard(id, userService.getUserId());
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "unLike board"
        );
    }

    @ApiOperation("모든 board 즐겨 찾기")
    @GetMapping(value = "/all_like_board")
    public ResponseEntity<ResponseDto> allLikeBoard(){
       List<Board>allLikeBoards =new ArrayList<>(boardLikeService.likeBoards(userService.getUserId()));
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            allLikeBoards
        );
    }
}