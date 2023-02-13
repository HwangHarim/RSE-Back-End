package com.game.core.file.application;

import com.game.core.file.domain.Photo;
import com.game.core.file.handler.FileHandler;
import com.game.core.file.infrastructure.FileRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileHandler fileHandler;
    private final FileRepository fileRepository;

    @Transactional
    public void save(List<MultipartFile> files) throws Exception{
        List<Photo> photoList = fileHandler.parseFileInfo(files);
        if (!photoList.isEmpty()) {
            fileRepository.saveAll(photoList);
        }
    }
}