package com.game.core.File.handler;


import com.game.core.File.domain.Photo;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHandler {
    public List<Photo> parseFileInfo(List<MultipartFile> multipartFiles) throws Exception {
        List<Photo> fileList = new ArrayList<>();
        if (multipartFiles.isEmpty())
            return fileList;

        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String currentDate = now.format(dateTimeFormatter);

            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            //프로젝트 폴더 내에 photo 폴더 생성 후 그 안에 현재 날짜로 파일 생성
            String path = "photo" + File.separator + currentDate;
            File file = new File(path);

            //경로가 존재하지 않으면 디렉토리 생성
            if (!file.exists()) {
                file.mkdirs();
            }

            for (MultipartFile multipartFile : multipartFiles) {
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                if (ObjectUtils.isEmpty(contentType))
                    break;
                else {
                    if (contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if (contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else
                        break;
                }

                //파일 이름이 중복되지 않도록 생성 시간의 나노초까지 구해서 이름으로 사용
                String newFileName = System.nanoTime() + originalFileExtension;
                Photo photo = Photo.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    //photo 파일 아래 날짜 파일 아래 파일을 저장
                    .filePath(path + File.separator + newFileName)
                    .fileSize(multipartFile.getSize())
                    .build();

                fileList.add(photo);

                file = new File(absolutePath + path + File.separator + newFileName);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);
            }
        }
        return fileList;
    }
}
