package com.game.rse.api.comment.infrastructure;

import com.game.rse.api.comment.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long boardId);
}
