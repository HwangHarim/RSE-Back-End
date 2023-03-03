package com.game.core.report.application;

import com.game.core.common.properties.SlackProperties;
import com.game.core.report.dto.request.CreateReportRequest;
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

    public void postSlackMessage(CreateReportRequest createReportRequest){
        try{
            MethodsClient methods = Slack.getInstance().methods(slackProperties.getToken());
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(slackProperties.getChannel())
                .text(reportContent(createReportRequest))
                .build();
            methods.chatPostMessage(request);
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }

    public String reportContent(CreateReportRequest createReportRequest) {
      return "id : " + createReportRequest.getUserId() +"\r"+
          "reportId : " +createReportRequest.getReportUserId() +"\r"+
          "BoardId : " +createReportRequest.getBoardId() +"\r"+
          "content : "+createReportRequest.getContent();
    }
}