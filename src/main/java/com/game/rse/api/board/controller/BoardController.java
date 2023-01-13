package com.game.rse.api.board.controller;

import com.game.rse.api.board.application.BoardService;
import com.game.rse.api.comment.dto.request.CreateCommentRequest;
import com.game.rse.api.comment.dto.response.ReadCommentRequest;
import com.game.rse.api.board.dto.request.CreateBoardRequest;
import com.game.rse.api.board.dto.request.UpdateBoardRequest;
import com.game.rse.api.comment.application.CommentService;
import com.game.rse.util.Handler.ResponseHandler;
import com.game.rse.util.ResponseDto.ResponseDto;
import com.game.rse.util.ResponseDto.ResponseMessage;
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
    private final CommentService commentService;
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

    @ApiOperation("board view 추가")
    @GetMapping("/{id}/read")
    public ResponseEntity<ResponseDto> viewCount(@PathVariable Long id) {
        Long views = boardService.allView(id); // views ++
        return  responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_VIEW_SUCCESS,
            views
        );
    }

    // 댓글 작성
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    @PostMapping("/{id}/comments")
    public ResponseEntity<ResponseDto> writeComment(@PathVariable("id") Long id, @RequestBody CreateCommentRequest createCommentRequest) {
        System.out.println(id);
        System.out.println(createCommentRequest.getContent());
        commentService.createComment(id, createCommentRequest);
        return responseHandler.toResponseEntity(
            ResponseMessage.CREATE_COMMENT_SUCCESS,
            "댓글 작성을 완료했습니다."
        );
    }


    // 게시글에 달린 댓글 모두 불러오기
    @ApiOperation(value = "댓글 불러오기", notes = "게시글에 달린 댓글을 모두 불러온다.")
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseDto> getComments(@PathVariable("id") Long boardId) {
        List<ReadCommentRequest> comments= commentService.getComments(boardId);
        return responseHandler.toResponseEntity(ResponseMessage.READ_COMMENT_SUCCESS, comments);
    }


    // 댓글 삭제
    @ApiOperation(value = "댓글 삭제", notes = "게시글에 달린 댓글을 삭제합니다.")
    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable("id") Long boardId, @PathVariable("commentId") Long commentId) {
        // 추후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서, 맞으면 삭제 진행하고
        // 틀리다면 예외처리를 해주면 된다.
        commentService.deleteComment(commentId);
        return responseHandler.toResponseEntity(ResponseMessage.DELETE_BOARD_SUCCESS, "댓글 삭제 완료");
    }
} 