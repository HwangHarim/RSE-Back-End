package com.game.core.board.infrastructure;

import com.game.core.board.domain.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BoardRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    @Override
    Page<Board> findAll(Specification<Board> spec, Pageable pageable);
    Page<Board> findByIdIn(List<Long> ids, Pageable pageable);
    List<Board> findBoardsByUserName(String userName);
    void deleteAllByUserId(String userId);
}