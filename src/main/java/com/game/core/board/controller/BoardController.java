package com.game.core.board.controller;

import com.game.core.board.application.BoardService;
import com.game.core.comment.dto.response.ReadCommentRequest;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.common.dto.ResponseDto;
import com.game.core.board.dto.request.CreateBoardRequest;
import com.game.core.board.dto.request.UpdateBoardRequest;
import com.game.core.comment.application.CommentService;
import com.game.core.common.util.Handler.ResponseHandler;
import com.game.core.common.dto.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
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
public class BoardController {

    private final BoardService boardService;
    private final ResponseHandler responseHandler;

    @ApiOperation("board 전체 검색")
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<ResponseDto> getAllBoards(
        @PageableDefault(sort ="id", direction= Direction.ASC)
        Pageable pageable){
        Page<CreateBoardRequest> boards = boardService.getBoards(pageable);
        return responseHandler.toResponseEntity(
            ResponseMessage.READ_ALL_BOARD_SUCCESS,
            boards
        );
    }

    @ApiOperation("board 생성")
    @PostMapping
    public ResponseEntity<ResponseDto> getBoard(@RequestBody CreateBoardRequest createBoardRequest){
        boardService.createBoard(createBoardRequest);
        return responseHandler.toResponseEntity(
            ResponseMessage.CREATE_BOARD_SUCCESS,
            "board 생성이 완료 되었습니다."
        );
    }

    @ApiOperation("board 검색")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> getBoard(@PathVariable("id") Long id){
       CreateBoardRequest board = boardService.findBoard(id);
        return responseHandler.toResponseEntity(
            ResponseMessage.READ_BOARD_SUCCESS,
            board
        );
    }

    @ApiOperation("board 수정")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> updateBoard(@PathVariable("id") Long id,
        @RequestBody UpdateBoardRequest updateBoardRequest){
        boardService.updateBoard(id, updateBoardRequest);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "board 수정이 완료 되었습니다."
        );
    }

    @ApiOperation("board 즐겨 찾기")
    @PatchMapping(value = "/{id}/like")
    public ResponseEntity<ResponseDto> updateLikeBoard(@PathVariable("id") Long id){
        boardService.setLike(id);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like_tag 변경"
        );
    }

    @ApiOperation("board 즐겨 찾기 해제")
    @PatchMapping(value = "/{id}/normal")
    public ResponseEntity<ResponseDto> updateUnLikeBoard(@PathVariable("id") Long id){
        boardService.setUnLike(id);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like_tag 변경"
        );
    }

    @ApiOperation("board 삭제")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseDto> deleteBoard(@PathVariable("id") Long id) {
      boardService.deleteBoard(id);
      return responseHandler.toResponseEntity(
          ResponseMessage.DELETE_BOARD_SUCCESS,
          "board 삭제 완료 되었습니다."
      );
    }

    /*
    * view 개수 추가 api
    * */
    @ApiOperation("board view 추가")
    @GetMapping("/read/{id}")
    public ResponseEntity<ResponseDto> viewCount(@PathVariable Long id) {
        int views = boardService.updateView(id); // views ++
        return  responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_VIEW_SUCCESS,
            views
        );
    }
} 