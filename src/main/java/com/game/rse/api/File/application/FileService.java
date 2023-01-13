package com.game.rse.api.File.application;

import com.game.rse.api.File.domain.Photo;
import com.game.rse.api.File.handler.FileHandler;
import com.game.rse.api.File.infrastructure.FileRepository;
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