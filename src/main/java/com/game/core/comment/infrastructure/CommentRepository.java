package com.game.core.comment.infrastructure;

import com.game.core.comment.domain.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByBoardId(Long boardId, Pageable pageable);

    Optional<Comment> findCommentByIdAndUserId(Long id, String userId);

    Page<Comment> findByIdIn(List<Long> commentIds, Pageable pageable);

    void deleteByIdAndUserId(Long id, String userId);

    void deleteByBoardId(Long boardId);

    void deleteAllByUserId(String userId);
}