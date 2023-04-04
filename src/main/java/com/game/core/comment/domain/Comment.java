package com.game.core.comment.domain;

import com.game.core.board.domain.vo.LikeTag;
import com.game.core.board.domain.Board;
import com.game.core.common.domain.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @Column(columnDefinition = "Long default 0", nullable = false)
    private Long likeViews;

    public void setBoard(Board board) {
        if (this.board != null) {
            this.board.getComments().remove(this);
        }
        this.board = board;
        //무한 루프 빠지지 않도록
        if (!board.getComments().contains(this))
            board.getComments().add(this);
    }

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