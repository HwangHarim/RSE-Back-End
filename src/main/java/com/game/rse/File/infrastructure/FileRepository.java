package com.game.rse.File.infrastructure;

import com.game.rse.File.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Photo, Long> {
}