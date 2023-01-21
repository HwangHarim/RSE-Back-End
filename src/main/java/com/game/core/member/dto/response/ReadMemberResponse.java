package com.game.core.member.dto.response;

public class ReadMemberResponse {
    private final String memberEmail;
    private final String memberNickname;
    private final String memberProfileUrl;
    private final String memberRole;

    public ReadMemberResponse(String memberEmail, String memberNickname, String memberProfile,
        String memberRole) {
        this.memberEmail = memberEmail;
        this.memberNickname = memberNickname;
        this.memberProfileUrl = memberProfile;
        this.memberRole = memberRole;
    }

    public static ReadMemberResponseBuilder builder() {
        return new ReadMemberResponseBuilder();
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public String getMemberProfileUrl() {
        return memberProfileUrl;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public static class ReadMemberResponseBuilder {

        private String email;
        private String nickname;
        private String profileUrl;
        private String role;

        private ReadMemberResponseBuilder() {
        }

        public ReadMemberResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public ReadMemberResponseBuilder nickname(final String nickname) {
            this.nickname = nickname;
            return this;
        }

        public ReadMemberResponseBuilder profileUrl(final String profileUrl) {
            this.profileUrl = profileUrl;
            return this;
        }

        public ReadMemberResponseBuilder role(final String role) {
            this.role = role;
            return this;
        }

        public ReadMemberResponse build() {
            return new ReadMemberResponse(this.email, this.nickname, this.profileUrl, this.role);
        }
    }
}
