package com.game.core.comment.controller;

import com.game.core.comment.application.CommentService;
import com.game.core.comment.dto.request.CreateCommentRequest;
import com.game.core.comment.dto.request.UpdateCommentRequest;
import com.game.core.comment.dto.response.ReadCommentResponse;
import com.game.core.common.response.handler.ResponseHandler;
import com.game.core.common.response.dto.ResponseDto;
import com.game.core.common.response.dto.ResponseMessage;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.annotation.AuthMember;
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
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ResponseHandler responseHandler;

    // 댓글 작성
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성한다.")
    @PostMapping("/{id}/comments")
    public ResponseEntity<ResponseDto> writeComment(
        @PathVariable("id") Long id,
        @AuthMember LoggedInMember loggedInMember,
        @RequestBody CreateCommentRequest createCommentRequest) {
        commentService.createComment(id,loggedInMember, createCommentRequest);
        return responseHandler.toResponseEntity(
            ResponseMessage.CREATE_COMMENT_SUCCESS,
            "댓글 작성을 완료했습니다."
        );
    }

    // 댓글 작성
    @ApiOperation("comment 수정")
    @PatchMapping(value = "/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> updateComment(
        @AuthMember LoggedInMember member,
        @PathVariable("id") Long id,
        @RequestBody UpdateCommentRequest updateCommentRequest){
        commentService.updateComment(id, updateCommentRequest, member);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_COMMENT_SUCCESS,
            "comment 수정이 완료 되었습니다."
        );
    }

    // 댓글 좋아요기능
    @ApiOperation("comment like")
    @PatchMapping(value = "/{boardId}/comments/{id}/like")
    public ResponseEntity<ResponseDto> upLikeView(@AuthMember LoggedInMember member,
        @PathVariable("id") Long id){
        commentService.upLikeViews(id, member);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like View 를 성공했습니다."
        );
    }

    // 댓글 좋아요 취소
    @ApiOperation("comment unlike")
    @PatchMapping(value = "/{boardId}/comments/{id}/unlike")
    public ResponseEntity<ResponseDto> downLikeView(@AuthMember LoggedInMember member,
        @PathVariable("id") Long id){
        commentService.downLikeViews(id, member);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like View 를 취소했습니다 ."
        );
    }

    // 게시글에 달린 댓글 모두 불러오기
    @ApiOperation(value = "댓글 불러오기", notes = "게시글에 달린 댓글을 모두 불러온다.")
    @GetMapping("{id}/comments")
    public ResponseEntity<ResponseDto> getComments(
        @PathVariable("id") Long boardId,
        @AuthMember LoggedInMember loggedInMember) {
        List<ReadCommentResponse> comments= commentService.getComments(boardId, loggedInMember);
        return responseHandler.toResponseEntity(ResponseMessage.READ_COMMENT_SUCCESS, comments);
    }

    // 댓글 삭제
    @ApiOperation(value = "댓글 삭제", notes = "게시글에 달린 댓글을 삭제합니다.")
    @DeleteMapping("/{boardId}/comments/{id}")
    public ResponseEntity<ResponseDto> deleteComment(
        @AuthMember LoggedInMember loggedInMember,
        @PathVariable("id") Long commentId) {
        commentService.deleteComment(commentId, loggedInMember);
        return responseHandler.toResponseEntity(ResponseMessage.DELETE_COMMENT_SUCCESS, "댓글 삭제 완료");
    }
}