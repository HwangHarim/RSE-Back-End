package com.game.core.file.infrastructure;

import com.game.core.file.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Photo, Long> {
}