package com.itshow.demo.controller;

import com.itshow.demo.dto.PagingDto;
import com.itshow.demo.dto.PostDto;
import com.itshow.demo.dto.Result;
import com.itshow.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseEntity getPosts(@RequestBody Pageable pageable) {

        try {
            PagingDto<List<PostDto>> postList = postService.getPostList(pageable);

            return new ResponseEntity<>(new Result(postList, false), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }
}
