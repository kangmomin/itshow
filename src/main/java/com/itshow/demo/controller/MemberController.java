package com.itshow.demo.controller;

import com.itshow.demo.config.security.Jwt.JwtConfig;
import com.itshow.demo.domain.Member;
import com.itshow.demo.dto.member.LoginDto;
import com.itshow.demo.dto.Result;
import com.itshow.demo.dto.member.SignUpDto;
import com.itshow.demo.exception.AlreadyExistIdException;
import com.itshow.demo.exception.MemberNotFoundException;
import com.itshow.demo.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtConfig jwtConfig;

    @PostMapping("/login")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {

        try {
            Member member = memberService.login(loginDto);
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Auth-Token", "Bearer " + jwtConfig.createToken(member.getLoginId()));
            headers.add("X-Refresh-Token", "Bearer " + jwtConfig.createRefreshToken(member.getLoginId()));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new Result(null, false));
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Result> signUp(@Valid @RequestBody SignUpDto signUpDto) {

        try {
            memberService.signUp(signUpDto);
            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (AlreadyExistIdException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }
}
