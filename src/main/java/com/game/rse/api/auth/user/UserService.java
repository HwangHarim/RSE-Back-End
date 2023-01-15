package com.game.rse.api.auth.user;

import static com.game.rse.api.auth.util.BaseResponseStatus.DATABASE_ERROR;
import static com.game.rse.api.auth.util.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;

import com.game.rse.api.auth.user.model.User;
import com.game.rse.api.auth.util.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserProvider userProvider;
    private final UserDao userDao;

    @Autowired
    public UserService( UserProvider userProvider, UserDao userDao) {
        this.userProvider = userProvider;
        this.userDao = userDao;
    }

    public User createUser(User user) throws BaseException {
        if (userProvider.checkEmail(user.getEmail()) == 1)
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        try {
            return this.userDao.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

    }

}
