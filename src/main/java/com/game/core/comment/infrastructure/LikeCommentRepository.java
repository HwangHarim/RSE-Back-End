package com.game.core.comment.infrastructure;

import com.game.core.comment.domain.LikeComment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    Optional<LikeComment> findByUserId(String userId);

}
