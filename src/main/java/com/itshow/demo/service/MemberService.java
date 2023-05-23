package com.itshow.demo.service;

import com.itshow.demo.common.Crypto;
import com.itshow.demo.domain.Member;
import com.itshow.demo.dto.LoginDto;
import com.itshow.demo.exception.MemberNotFoundException;
import com.itshow.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j @Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder pwdEncoder;

    public void login(LoginDto loginDto) throws Exception {

        Member member = memberRepository.findByLoginId(loginDto.getLoginId());
        if (member == null || // id check
                // password가 일치하는지 확인
                !pwdEncoder.matches(loginDto.getPassword(), member.getPassword())
        ) throw new MemberNotFoundException();
    }
}
