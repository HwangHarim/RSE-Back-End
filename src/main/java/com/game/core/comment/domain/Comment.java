package com.game.core.comment.domain;

import com.game.core.common.domain.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
@DynamicInsert
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id")
    private Long boardId;
    private String content;

    private String userId;

    private String userName;
    @Column(columnDefinition = "Long default 0", nullable = false)
    private Long likeViews;

    public void upLikeView(Long likeViews){
        this.likeViews = likeViews+1;
    }

    public void downLikeView(Long likeViews){
        if(likeViews != 0){
            this.likeViews = likeViews-1;
        }

    }
    public void update(String comments){
        this.content = comments;
    }
}