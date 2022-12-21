package com.game.rse.File.controller;

import com.game.rse.File.application.FileService;
import com.game.rse.util.Handler.ResponseHandler;
import com.game.rse.util.ResponseDto.ResponseDto;
import com.game.rse.util.ResponseDto.ResponseMessage;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api("photo")
@RestController
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final ResponseHandler responseHandler;
    private final FileService fileService;

    @PostMapping(value = "/uploadFile")
    public ResponseEntity<ResponseDto> uploadFile(
        @RequestParam(value = "file", required = false) List<MultipartFile> files)
        throws Exception {
        fileService.save(files);
        return responseHandler.toResponseEntity(ResponseMessage.SAVE_IMAGE_SUCCESS, "이미지 업로드 완료");
    }
}
