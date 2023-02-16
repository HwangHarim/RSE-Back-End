package com.game.core.comment.Controller;

import com.game.core.comment.application.CommentService;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.request.UpdateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentRequest;
import com.game.core.common.dto.Response.Handler.ResponseHandler;
import com.game.core.common.dto.Response.ResponseDto;
import com.game.core.common.dto.Response.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("comment")
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ResponseHandler responseHandler;

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

    // 댓글 작성
    @ApiOperation("comment 수정")
    @PatchMapping(value = "/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> updateComment(@PathVariable("id") Long id,
        @RequestBody UpdateCommentRequest updateCommentRequest){
        commentService.updateComment(id, updateCommentRequest);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "comment 수정이 완료 되었습니다."
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
    @DeleteMapping("/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> deleteComment(@PathVariable("boardId")Long boardId,
                                                     @PathVariable("id") Long commentId) {
        // 추후 JWT 로그인 기능을 추가하고나서, 세션에 로그인된 유저와 댓글 작성자를 비교해서, 맞으면 삭제 진행하고
        // 틀리다면 예외처리를 해주면 된다.
        commentService.deleteComment(commentId);
        return responseHandler.toResponseEntity(ResponseMessage.DELETE_BOARD_SUCCESS, "댓글 삭제 완료");
    }
}