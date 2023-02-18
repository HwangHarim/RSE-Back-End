package com.game.core.file.application;

import com.game.core.file.domain.Photo;
import com.game.core.file.handler.PhotoHandler;
import com.game.core.file.infrastructure.PhotoRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoHandler fileHandler;
    private final PhotoRepository fileRepository;

    @Transactional
    public void save(List<MultipartFile> files) throws Exception{
        List<Photo> photoList = fileHandler.parseFileInfo(files);
        if (!photoList.isEmpty()) {
            fileRepository.saveAll(photoList);
        }
    }
}