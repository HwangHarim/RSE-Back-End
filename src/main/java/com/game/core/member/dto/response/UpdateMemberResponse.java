package com.game.core.member.dto.response;

public class UpdateMemberResponse {
    private final String nickname;
    private final String profileUrl;

    public UpdateMemberResponse(String nickname, String profileUrl) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
