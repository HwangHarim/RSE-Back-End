package com.game.core.member.application;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.member.converter.MemberConverter;
import com.game.core.member.domain.Member;
import com.game.core.member.dto.bundle.ReadMemberBundle;
import com.game.core.member.dto.bundle.UpdateMemberBundle;
import com.game.core.member.dto.response.ReadMemberResponse;
import com.game.core.member.dto.response.UpdateMemberResponse;
import com.game.core.member.infrastructure.MemberRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Transactional
    public ReadMemberResponse find (ReadMemberBundle bundle) {
        Member member = findById(bundle.getMemberId());

        return memberConverter.toReadMemberResponse(member);
    }

    @Transactional
    public Member findByEmailForSearch(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new NullPointerException());
    }

    @Transactional
    public UpdateMemberResponse update(UpdateMemberBundle bundle) {
        Member member = findById(bundle.getMemberId());
        member.update(bundle.getNickname(), bundle.getProfileUrl());

        return memberConverter.toUpdateMemberResponse(member);
    }

    @Transactional
    public Member findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new NullPointerException());
    }
}
