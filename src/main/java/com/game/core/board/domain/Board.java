package com.game.core.board.domain;

import com.game.core.board.domain.vo.Type;
import com.game.core.comment.domain.Comment;
import com.game.core.common.domain.BaseTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@DynamicInsert
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String userName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "Long default 0", nullable = false)
    private Long view;

    @Column(columnDefinition = "Long default 0", nullable = false)
    private Long likeCount;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);

        if (comment.getBoard() != this)
            comment.setBoard(this);
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Long updateView(Long view){
        this.view = view+1;
        return this.view;
    }
    public Long updateUpLikeCount(Long likeCount){
        this.likeCount = likeCount+1;
        return this.likeCount;

    }public Long updateDownLikeCount(Long likeCount){
        this.likeCount = likeCount-1;
        return this.likeCount;
    }
    public void setUserName(String userName) {
      this.userName = userName;
    }

    public void changeTage(Type type){
        this.type = type;
    }
}