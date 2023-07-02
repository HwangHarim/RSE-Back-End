package com.game.core.board.infrastructure;

import com.game.core.board.domain.LikeBoard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    Optional<LikeBoard> findByUserId(String UserId);
}
