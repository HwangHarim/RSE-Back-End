package com.game.core.board.domain;

import com.game.core.board.domain.vo.Type;
import com.game.core.board.dto.request.board.UpdateBoardRequest;
import com.game.core.common.domain.BaseTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private String userId;
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


    public void update(UpdateBoardRequest updateBoardRequest){
        this.title = updateBoardRequest.getTitle();
        this.type = Type.valueOf(updateBoardRequest.getType());
        this.content = updateBoardRequest.getContent();
    }

    public void updateView(Long view){
        this.view = view+1;
    }

    public void updateUpLikeCount(Long likeCount){
        this.likeCount = likeCount+1;

    }

    public void updateDownLikeCount(Long likeCount){
        this.likeCount = likeCount-1;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }
}