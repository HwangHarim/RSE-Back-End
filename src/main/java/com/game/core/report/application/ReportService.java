package com.game.core.report.application;

import com.game.core.common.properties.SlackProperties;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.report.dto.request.CreateReportRequest;
import com.game.core.report.dto.response.ReadReportResponse;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final SlackProperties slackProperties;
    private final ReadReportResponse reportConvert;

    public void postSlackMessage(CreateReportRequest createReportRequest, LoggedInMember member){
        try{
            MethodsClient methods = Slack.getInstance().methods(slackProperties.getToken());
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(slackProperties.getChannel())
                .text(reportConvert.reportContent(createReportRequest, member))
                .build();
            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}