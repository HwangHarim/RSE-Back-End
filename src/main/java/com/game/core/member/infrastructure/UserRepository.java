package com.game.core.member.infrastructure;

import com.game.core.member.domain.User;
import java.lang.StackWalker.Option;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);
    User findByUsername(String userName);
}