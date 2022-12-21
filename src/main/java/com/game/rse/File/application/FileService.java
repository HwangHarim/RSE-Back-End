package com.game.rse.File.application;

import com.game.rse.File.domain.Photo;
import com.game.rse.File.handler.FileHandler;
import com.game.rse.File.infrastructure.FileRepository;
import com.game.rse.board.domain.Board;
import com.game.rse.board.domain.vo.LikeTag;
import com.game.rse.board.domain.vo.Type;
import com.game.rse.board.dto.request.CreateBoardRequest;
import com.game.rse.board.infrastructure.BoardRepository;
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