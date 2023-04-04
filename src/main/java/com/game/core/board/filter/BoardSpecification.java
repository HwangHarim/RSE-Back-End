package com.game.core.board.filter;

import com.game.core.board.domain.Board;
import com.game.core.board.domain.vo.Type;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {
    public static Specification<Board> searchLecture(Type type){
        return new Specification<Board>() {
            @Override
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("type"), type);
            }
        };
    }

    public static Specification<Board> searchTitleLecture(String title){
        return new Specification<Board>() {
            @Override
            public Predicate toPredicate(Root<Board> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("title"), "%"+title+"%");
            }
        };
    }
}