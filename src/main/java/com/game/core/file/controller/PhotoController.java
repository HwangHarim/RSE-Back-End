package com.game.core.file.controller;

import com.game.core.common.dto.Response.ResponseDto;
import com.game.core.file.application.PhotoService;
import com.game.core.common.dto.Response.Handler.ResponseHandler;
import com.game.core.common.dto.Response.ResponseMessage;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Api("photo")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PhotoController {

    private final ResponseHandler responseHandler;
    private final PhotoService photoService;

    @PostMapping(value = "/photo")
    public ResponseEntity<ResponseDto> uploadFile(
        @RequestParam(value = "file", required = false) List<MultipartFile> files)
        throws Exception {
        photoService.save(files);
        return responseHandler.toResponseEntity(ResponseMessage.SAVE_IMAGE_SUCCESS, "이미지 업로드 완료");
    }
}
