package com.game.core.comment.domain;

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
@Table(name = "like_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LikeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ElementCollection
    @Column(name = "user_like_comments_list")
    private List<Long> likeCommentIds = new ArrayList<>();

    public LikeComment(String userId) {
      this.userId = userId;
    }

    public void updateLikeComment(Long commentId){
        this.likeCommentIds.add(commentId);
    }

    public void updateUnlikeComment(Long commentId){
        this.likeCommentIds.remove(commentId);
    }
}
