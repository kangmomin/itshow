package com.itshow.demo.config.controller;

import com.itshow.demo.dto.LoginDto;
import com.itshow.demo.dto.Result;
import com.itshow.demo.exception.MemberNotFoundException;
import com.itshow.demo.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Result> login(@Valid @RequestBody LoginDto loginDto) {

        try {
            memberService.login(loginDto);
            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (MemberNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }
}
