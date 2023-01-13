package com.game.rse.api.File.infrastructure;

import com.game.rse.api.File.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Photo, Long> {
}