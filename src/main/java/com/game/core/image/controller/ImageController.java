package com.game.core.image.controller;

import com.game.core.common.response.dto.ResponseDto;
import com.game.core.common.response.dto.ResponseMessage;
import com.game.core.common.response.handler.ResponseHandler;
import com.game.core.image.application.S3Uploader;
import com.game.core.image.dto.response.UploadBundle;
import com.game.core.image.dto.response.UploadResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3Uploader s3Uploader;
    private final ResponseHandler responseHandler;

    @PostMapping
    public ResponseEntity<ResponseDto> upload(
        @RequestParam("image") MultipartFile multipartFile)
        throws IOException {
        UploadBundle bundle = new UploadBundle(multipartFile, "temp");
        UploadResponse response = s3Uploader.upload(bundle);

        return responseHandler.toResponseEntity(ResponseMessage.UPLOAD_IMAGE_SUCCESS, response);
    }
}