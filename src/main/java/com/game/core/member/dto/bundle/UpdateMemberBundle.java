package com.game.core.member.dto.bundle;

import com.game.core.member.dto.request.UpdateMemberRequest;

public class UpdateMemberBundle {
    private final Long memberId;
    private final String nickname;
    private final String profileUrl;

    public UpdateMemberBundle(Long memberId, UpdateMemberRequest request) {
        this.memberId = memberId;
        this.nickname = request.getNickname();
        this.profileUrl = request.getProfileUrl();
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}