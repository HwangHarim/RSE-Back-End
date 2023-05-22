package com.game.core.common.response.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseMessage {


    //user message
    USER_INFO(HttpStatus.OK, "User 정보"),
    UPDATE_USER_NAME(HttpStatus.OK, "User 이름 변경 완료"),
    DELETE_USER(HttpStatus.OK, "user 삭제 완료"),


    //token message
    CREATE_TOKEN_SUCCESS(HttpStatus.CREATED, "토큰 생성 완료 "),

    /*
    * board message
    **/
    CREATE_BOARD_SUCCESS(HttpStatus.CREATED, "Board 추가 성공"),
    UPDATE_BOARD_SUCCESS(HttpStatus.OK,"Board 수정 성공"),
    READ_ALL_BOARD_SUCCESS(HttpStatus.OK,"Board 전체 조회 성공"),
    READ_BOARD_SUCCESS(HttpStatus.OK,"Board 아이디 조회 성공"),
    DELETE_BOARD_SUCCESS(HttpStatus.OK, "Board 삭제 성공"),
    UPDATE_BOARD_VIEW_SUCCESS(HttpStatus.OK, "Board View 추가 성공"),

    //comment
    CREATE_COMMENT_SUCCESS(HttpStatus.CREATED, "댓글 추가 성공"),
    UPDATE_COMMENT_SUCCESS(HttpStatus.OK,"댓글 수정 성공"),
    READ_COMMENT_SUCCESS(HttpStatus.OK,"댓글 조회 성공"),
    DELETE_COMMENT_SUCCESS(HttpStatus.OK,"댓글 삭제 성공"),

    //image
    SAVE_IMAGE_SUCCESS(HttpStatus.OK,"이미지 업로드 성공"),

    UPLOAD_IMAGE_SUCCESS(HttpStatus.OK,"이미지 업로드 성공");

    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";


    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}