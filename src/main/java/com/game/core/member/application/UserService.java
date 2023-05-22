package com.game.core.member.application;

import com.game.core.board.domain.Board;
import com.game.core.board.infrastructure.BoardRepository;
import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.member.DuplicateUserException;
import com.game.core.member.domain.User;
import com.game.core.member.dto.LoggedInMember;
import com.game.core.member.dto.request.UpdateUserName;
import com.game.core.member.infrastructure.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public void removeUser(LoggedInMember member){
        List<Board> boards = boardRepository.findBoardsByUserName(member.getId());
        boardRepository.deleteAll(boards);
        userRepository.delete(userRepository.findByUserId(member.getId()));
    }

    public void updateUserName(String userId, UpdateUserName updateUserName){
        User user = userRepository.findByUserId(userId);
        if(userRepository.findByUsername(updateUserName.getUserName()) == null){
            user.updateUsername(updateUserName.getUserName());
            userRepository.save(user);
        }else if(userRepository.findByUsername(updateUserName.getUserName()) != null){
            throw new DuplicateUserException(ErrorMessage.DUPLICATE_NAME);
        }
    }

    public LoggedInMember viewUser(String userId){
        return LoggedInMember.from(userRepository.findByUserId(userId));
    }
}