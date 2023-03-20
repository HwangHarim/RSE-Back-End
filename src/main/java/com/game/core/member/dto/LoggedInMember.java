package com.game.core.member.dto;

import com.game.core.member.domain.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class LoggedInMember {
    private String id;
    private String email;
    private String profileUrl;

    private String userName;

    protected LoggedInMember() {
    }

    @Builder
    protected LoggedInMember(String id, String email, String profileUrl,String userName) {
        this.id = id;
        this.email = email;
        this.profileUrl = profileUrl;
        this.userName = userName;
    }

    public static LoggedInMember from(User member) {
        return LoggedInMember.builder()
            .id(member.getUserId())
            .email(member.getEmail())
            .profileUrl(member.getProfileImageUrl())
            .userName(member.getUsername())
            .build();
    }
}