package com.game.core.error.dto;

import java.util.Arrays;
import org.springframework.http.HttpStatus;

public enum ErrorMessage {

    /*
     * 보드 관련 오류
     **/
    NOT_FIND_ID_BOARD(HttpStatus.BAD_REQUEST, "요청한 보드가 존재하지 않습니다."),
    NOT_FIND_ID_USER(HttpStatus.BAD_REQUEST, "요한한 사용자가 존재하지 않습니다."),

    /*
     * 토큰 관련 오류
     **/
    NOT_GENERATE_TOKEN(HttpStatus.BAD_REQUEST, "토큰 생성에 실패했습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST,"잘못된 액세스 토큰."),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST,"잘못된 리플레쉬 토큰."),
    NOT_EXPIRED_TOKEN_YET(HttpStatus.BAD_REQUEST, "아직 만료되지 않은 토큰입니다"),

    /*
     * oauth message
     **/
    OAuth_Provider_Miss_Match(HttpStatus.BAD_REQUEST, "OAuth 공급자와 불일치 합니다."),
    NO_AUTH(HttpStatus.BAD_REQUEST, "권한이 존재하지 않습니다."),

    /*
     * member 관련 오류
     **/
    DUPLICATE_NAME(HttpStatus.BAD_REQUEST,"중복되는 이름 입니다."),
    NOT_FIND_USER_MESSAGE(HttpStatus.NOT_FOUND, "해당 사용자를 찾지 못했습니다."),

    /*
    * 서버 내부 오류
    **/
    INTERNAL_SERVER_ERROR(HttpStatus.BAD_REQUEST, "내부 서버 오류");
    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }
}