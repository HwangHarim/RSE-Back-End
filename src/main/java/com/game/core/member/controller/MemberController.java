package com.game.core.member.controller;

import com.game.core.common.dto.ResponseDto;
import com.game.core.common.dto.ResponseMessage;
import com.game.core.common.util.Handler.ResponseHandler;
import com.game.core.common.util.SecurityUtils;
import com.game.core.member.application.MemberService;
import com.game.core.member.dto.bundle.ReadMemberBundle;
import com.game.core.member.dto.bundle.UpdateMemberBundle;
import com.game.core.member.dto.request.UpdateMemberRequest;
import com.game.core.member.dto.response.ReadMemberResponse;
import com.game.core.member.dto.response.UpdateMemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Member")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ResponseHandler responseHandler;

    @ApiOperation("프로필 조회")
    @GetMapping
    public ResponseEntity<ResponseDto> getProfile() {
        Long memberId = SecurityUtils.getCurrentUsername();

        ReadMemberBundle bundle = new ReadMemberBundle(memberId);
        ReadMemberResponse response = memberService.find(bundle);

        return responseHandler.toResponseEntity(ResponseMessage.CREATE_BOARD_SUCCESS, response);
    }

    @ApiOperation("프로필 수정")
    @PatchMapping
    public ResponseEntity<ResponseDto> update(
        @RequestBody UpdateMemberRequest request) {
        Long memberId = SecurityUtils.getCurrentUsername();

        UpdateMemberBundle bundle = new UpdateMemberBundle(memberId, request);
        UpdateMemberResponse response = memberService.update(bundle);

        return responseHandler.toResponseEntity(ResponseMessage.UPDATE_BOARD_SUCCESS, response);
    }
}
