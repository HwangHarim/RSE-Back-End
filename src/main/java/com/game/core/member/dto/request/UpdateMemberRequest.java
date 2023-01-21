package com.game.core.member.dto.request;

public class UpdateMemberRequest {
    private final String nickname;
    private final String profileUrl;

    public UpdateMemberRequest(String nickname, String profileUrl) {
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
