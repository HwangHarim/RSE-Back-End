package com.game.core.member.infrastructure;

import com.game.core.member.domain.MemberRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    MemberRefreshToken findByOauthPermission(String oauthPermission);

    MemberRefreshToken findByOauthPermissionAndRefreshToken(String oauthPermission,
        String refreshToken);
}