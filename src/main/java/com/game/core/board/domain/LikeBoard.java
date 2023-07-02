package com.game.core.board.domain;

import com.game.core.common.domain.BaseTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "like_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeBoard extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ElementCollection
    @Column(name = "user_like_list")
    private List<Long> likeIds = new ArrayList<>();;

    public LikeBoard(String userId){
        this.userId = userId;
    }

    public void updateLikeBoard(Long boardId){
        this.likeIds.add(boardId);
    }

    public void updateUnlikeBoard(Long boardId){
        this.likeIds.remove(boardId);
    }
}