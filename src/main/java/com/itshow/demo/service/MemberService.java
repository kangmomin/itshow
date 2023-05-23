package com.itshow.demo.service;

import com.itshow.demo.common.Crypto;
import com.itshow.demo.domain.Member;
import com.itshow.demo.dto.LoginDto;
import com.itshow.demo.exception.MemberNotFoundException;
import com.itshow.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j @Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void login(LoginDto loginDto) throws Exception {

        Member member = memberRepository.findByLoginId(loginDto.getLoginId());
        if (member == null || // id check
                // password check
                !Objects.equals(Crypto.encrypt(loginDto.getPassword(),
                        member.getSalt()), member.getPassword())
        ) throw new MemberNotFoundException();
    }
}
