package com.game.rse.api.board.domain;

import com.game.rse.api.File.domain.Photo;
import com.game.rse.api.board.domain.vo.LikeTag;
import com.game.rse.api.board.domain.vo.Type;
import com.game.rse.api.comment.domain.Comment;
import com.game.rse.util.domain.BaseTime;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String nickName;

    @Column(nullable = false)
    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private LikeTag likeTag;

    @Column(columnDefinition = "Long default 0", nullable = false)
    private Long view;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Photo> photos = new ArrayList<>();

    public void addFile(Photo file) {
        this.photos.add(file);

        if (file.getBoard() != this)
            file.setBoard(this);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);

        if (comment.getBoard() != this)
            comment.setBoard(this);
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void changeTage(Type type){
        this.type = type;
    }

    public void upView(Long view){
        ++view;
        this.view = view;
    }

    public void setLikeTag(LikeTag likeTag){
        this.likeTag = likeTag;
    }
}