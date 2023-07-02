package com.game.core.member.application;

import com.game.core.board.domain.Board;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.comment.infrastructure.CommentRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.member.DuplicateUserException;
import com.game.core.error.exception.member.NotFoundUserException;
import com.game.core.member.domain.User;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.dto.request.UpdateUserName;
import com.game.core.member.infrastructure.UserRepository;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void removeUser(LoggedInMember member){
        boardRepository.deleteAllByUserId(member.getId());
        commentRepository.deleteAllByUserId(member.getId());
        userRepository.delete(userRepository.findByUserId(member.getId()));
    }

    public void updateUserName(LoggedInMember user, UpdateUserName updateUserName){
        User users = userRepository.findByUserId(user.getId());
        List<Board> boards = new ArrayList<>();
        if(userRepository.findByUsername(updateUserName.getUserName()) == null){
            users.updateUsername(updateUserName.getUserName());
            for(Board change : boardRepository.findBoardsByUserName(user.getUserName())){
                change.setUserName(updateUserName.getUserName());
                boards.add(change);
            }
            boardRepository.saveAll(boards);
        }else if(userRepository.findByUsername(updateUserName.getUserName()) != null){
            throw new DuplicateUserException(ErrorMessage.DUPLICATE_NAME);
        }
    }

    public LoggedInMember viewUser(String userId){
        return LoggedInMember.from(
            userRepository.findByUserId(userId)
        );
    }
}