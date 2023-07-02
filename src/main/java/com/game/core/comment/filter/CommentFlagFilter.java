package com.game.core.comment.filter;

import com.game.core.comment.domain.LikeComment;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class CommentFlagFilter {
    public boolean mineFlag(String commentUserId, String userId){
        return commentUserId.equals(userId);
    }

    public boolean likeFlag(Long commentId, Optional<LikeComment> likeCommentIds){
        if(likeCommentIds.isEmpty()){
            return false;
        }
        return likeCommentIds.get().getLikeCommentIds().contains(commentId);
    }
}
