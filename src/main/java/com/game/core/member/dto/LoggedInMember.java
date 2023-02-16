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

    protected LoggedInMember() {
    }

    @Builder
    protected LoggedInMember(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public static LoggedInMember from(User member) {
        return LoggedInMember.builder()
            .id(member.getUserId())
            .email(member.getEmail())
            .build();
    }
}
