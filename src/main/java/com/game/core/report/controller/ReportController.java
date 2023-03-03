package com.game.core.report.controller;

import com.game.core.common.dto.Response.Handler.ResponseHandler;
import com.game.core.common.dto.Response.ResponseDto;
import com.game.core.common.dto.Response.ResponseMessage;
import com.game.core.report.application.ReportService;
import com.game.core.report.dto.request.CreateReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService slackService;
    private final ResponseHandler responseHandler;

    @PostMapping
    public ResponseEntity<ResponseDto> postReport(@RequestBody CreateReportRequest createReportRequest){
        slackService.postSlackMessage(createReportRequest);
        return responseHandler.toResponseEntity(
            ResponseMessage.CREATE_BOARD_SUCCESS,
            "신고가 완료 되었습니다.");
    }
}