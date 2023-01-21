package com.game.core.member.domain;

import com.game.core.common.domain.BaseTime;
import com.game.core.member.domain.vo.ProviderType;
import com.game.core.member.domain.vo.Role;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Member extends BaseTime {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    private String nickname;

    private String profileUrl;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "member_provider_type")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(name = "member_oauth_permission", unique = true)
    private String oauthPermission;

    protected Member() {

    }

    public Member(
        String oauthPermission,
        String nickname,
        String profileUrl,
        String email,
        ProviderType providerType
    ){
        this.oauthPermission = oauthPermission;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.providerType = providerType;
        this.role = Role.USER;
    }

    public static MemberBuilder builder() {
        return new Member.MemberBuilder();
    }

    public void update(String nickname, String profileUrl) {
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }

    public Long getId() {
        return id;
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

    public String getRoleValue() {
        return role.getRole();
    }

    public ProviderType getProviderType() {
        return providerType;
    }

    public String getOauthPermission() {
        return oauthPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email)
            && Objects.equals(nickname, member.nickname)
            && Objects.equals(profileUrl, member.profileUrl)
            && role == member.role
            && providerType == member.providerType
            && Objects.equals(oauthPermission, member.oauthPermission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickname, role, providerType,profileUrl,
            oauthPermission);
    }

    public static class MemberBuilder {

        private String oauthPermission;
        private String nickname;
        private String email;
        private String profileUrl;
        private ProviderType providerType;

        private MemberBuilder() {
        }

        public MemberBuilder oauthPermission(final String oauthPermission) {
            this.oauthPermission = oauthPermission;
            return this;
        }

        public MemberBuilder profileUrl(final String profileUrl) {
            this.profileUrl = profileUrl;
            return this;
        }

        public MemberBuilder nickname(final String nickname) {
            this.nickname = nickname;
            return this;
        }

        public MemberBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public MemberBuilder providerType(ProviderType providerType) {
            this.providerType = providerType;
            return this;
        }

        public Member build() {
            return new Member(this.oauthPermission, this.nickname,this.profileUrl, this.email, this.providerType);
        }
    }
}