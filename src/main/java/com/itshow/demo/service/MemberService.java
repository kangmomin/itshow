package com.itshow.demo.service;

import com.itshow.demo.domain.Member;
import com.itshow.demo.dto.LoginDto;
import com.itshow.demo.dto.SignUpDto;
import com.itshow.demo.exception.AlreadyExistIdException;
import com.itshow.demo.exception.MemberNotFoundException;
import com.itshow.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j @Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder pwdEncoder;

    public Member login(LoginDto loginDto) throws Exception {

        Member member = memberRepository.findByLoginId(loginDto.getLoginId());
        if (member == null || // id check
                // password가 일치하는지 확인
                !pwdEncoder.matches(loginDto.getPassword(), member.getPassword())
        ) throw new MemberNotFoundException();

        return member;
    }

    public void signUp(SignUpDto signUpDto) throws AlreadyExistIdException {
        String encodedPassword = pwdEncoder.encode(signUpDto.getPassword());
        Member validLoginId = memberRepository.findByLoginId(signUpDto.getLoginId());

        if (validLoginId != null) throw new AlreadyExistIdException();

        Member member = new Member(signUpDto.getName(),
                signUpDto.getLoginId(), encodedPassword);
        memberRepository.save(member);
    }
}
