package com.game.core.board.filter;

import com.game.core.board.domain.LikeBoard;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class BoardFlagFilter {

    public boolean mineFlag(String boardUserId, String userId){
        return boardUserId.equals(userId);
    }

    public boolean likeFlag(Long boardId, Optional<LikeBoard> likeBoard){
        if(likeBoard.isEmpty()){
            return false;
        }
        return likeBoard.get().getLikeIds().contains(boardId);
    }
}
