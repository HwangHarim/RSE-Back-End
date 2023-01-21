package com.game.core.member.dto.response;

public class SearchMemberResponse {
    private final Long memberId;
    private final String email;
    private final String nickname;
    private final String profileUrl;

    public SearchMemberResponse(Long memberId, String email, String nickname,
        String profileUrl) {
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public static SearchMemberResponseBuilder builder() {
        return new SearchMemberResponseBuilder();
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public static class SearchMemberResponseBuilder {

        private Long memberId;
        private String email;
        private String nickname;
        private String profileUrl;

        private SearchMemberResponseBuilder() {
        }

        public SearchMemberResponseBuilder memberId(final Long memberId) {
            this.memberId = memberId;
            return this;
        }

        public SearchMemberResponseBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public SearchMemberResponseBuilder nickname(final String nickname) {
            this.nickname = nickname;
            return this;
        }

        public SearchMemberResponseBuilder profileUrl(final String profileUrl) {
            this.profileUrl = profileUrl;
            return this;
        }

        public SearchMemberResponse build() {
            return new SearchMemberResponse(this.memberId, this.email, this.nickname, this.profileUrl);
        }
    }
}
