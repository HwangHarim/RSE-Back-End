package com.game.core.board.infrastructure;

import com.game.core.board.domain.BoardLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardIdAndMemberId(Long boardId, Long memberId);
    void  deleteByBoardIdAndMemberId(Long boardId, Long memberId);

}
