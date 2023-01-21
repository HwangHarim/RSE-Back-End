package com.game.core.member.converter;

import com.game.core.member.domain.Member;
import com.game.core.member.dto.response.ReadMemberResponse;
import com.game.core.member.dto.response.SearchMemberResponse;
import com.game.core.member.dto.response.UpdateMemberResponse;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {
//    public Member toMember(OAuth2MemberInfo userInfo, ProviderType providerType) {
//        return Member.builder()
//            .oauthPermission(userInfo.getOauthPermission())
//            .nickname(userInfo.getNickname())
//            .email(userInfo.getEmail())
//            .profileUrl(userInfo.get)
//            .providerType(providerType)
//            .build();
//    }

    public ReadMemberResponse toReadMemberResponse(Member member) {
        return ReadMemberResponse.builder()
            .email(member.getEmail())
            .nickname(member.getNickname())
            .profileUrl(member.getProfileUrl())
            .role(member.getRoleValue())
            .build();
    }

    public SearchMemberResponse toSearchMemberResponse(Member member) {
        return SearchMemberResponse.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .nickname(member.getNickname())
            .profileUrl(member.getProfileUrl())
            .build();
    }

    public UpdateMemberResponse toUpdateMemberResponse(Member member) {
        return new UpdateMemberResponse(member.getNickname(), member.getProfileUrl());
    }
}
