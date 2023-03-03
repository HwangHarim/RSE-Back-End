package com.game.core.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.game.core.common.domain.BaseTime;
import com.game.core.oauth.domain.ProviderType;
import com.game.core.oauth.domain.RoleType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseTime {
    @JsonIgnore
    @Id
    @Column(name = "user_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "user_id", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(name = "username", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @JsonIgnore
    @Column(name = "password", length = 128)
    @NotNull
    @Size(max = 128)
    private String password;

    @Column(name = "email", length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(name = "email_verified_un", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String emailVerifiedYn;

    @Column(name = "profile_image_url", length = 512)
    @NotNull
    @Size(max = 512)
    private String profileImageUrl;

    @Column(name = "provider_type", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(name = "role_type", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

    public void updateUsername(String username){
        this.username = username;
    }

    public User(
            @NotNull @Size(max = 64) String userId,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String emailVerifiedYn,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType
    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}