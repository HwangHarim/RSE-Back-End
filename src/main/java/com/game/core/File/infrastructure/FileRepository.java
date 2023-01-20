package com.game.core.File.infrastructure;

import com.game.core.File.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Photo, Long> {
}