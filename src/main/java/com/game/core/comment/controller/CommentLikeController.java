package com.game.core.comment.controller;

import com.game.core.comment.application.LikeCommentService;
import com.game.core.common.response.dto.ResponseDto;
import com.game.core.common.response.dto.ResponseMessage;
import com.game.core.common.response.handler.ResponseHandler;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.infrastructure.annotation.AuthMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("comment_like")
@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class CommentLikeController {

    private final LikeCommentService likeCommentService;
    private final ResponseHandler responseHandler;

     //댓글 좋아요기능
    @ApiOperation("comment like")
    @PostMapping(value = "/{boardId}/comments/{id}/like")
    public ResponseEntity<ResponseDto> upLikeView(@AuthMember LoggedInMember member,
        @PathVariable("id") Long id){
        likeCommentService.upLikeViews(id, member);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like View 를 성공했습니다."
        );
    }

    // 댓글 좋아요 취소
    @ApiOperation("comment unlike")
    @PatchMapping(value = "/{boardId}/comments/{id}/unlike")
    public ResponseEntity<ResponseDto> downLikeView(
        @AuthMember LoggedInMember member,
        @PathVariable("id") Long id){
       likeCommentService.downLikeViews(id, member);
        return responseHandler.toResponseEntity(
            ResponseMessage.UPDATE_BOARD_SUCCESS,
            "like View 를 취소했습니다 ."
        );
    }
}