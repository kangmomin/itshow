package com.itshow.demo.controller;

import com.itshow.demo.dto.*;
import com.itshow.demo.dto.post.PagingDto;
import com.itshow.demo.dto.post.PostDto;
import com.itshow.demo.dto.post.UpdatePostDto;
import com.itshow.demo.dto.post.WritePostDto;
import com.itshow.demo.exception.FavoriteNotFoundException;
import com.itshow.demo.exception.PostNotFoundException;
import com.itshow.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/post")
    public ResponseEntity getPosts(@PageableDefault(size = 15, sort = "desc") Pageable pageable) {

        try {
            PagingDto<List<PostDto>> postList = postService.getPostList(pageable);

            return new ResponseEntity<>(new Result(postList, false), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/post/write")
    public ResponseEntity writePost(@RequestBody WritePostDto writePostDto) {

        try {
            postService.writePost(writePostDto);

            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/post/update")
    public ResponseEntity updatePost(@RequestBody UpdatePostDto updatePostDto) {

        try {
            postService.updatePost(updatePostDto);

            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/post/delete/{id}")
    public ResponseEntity deletePost(@PathVariable("id") Long postId) {

        try {
            postService.deletePost(postId);

            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/favorite/{id}")
    public ResponseEntity favoritePost(@PathVariable("id") Long postId) {

        try {
            postService.favoritPost(postId);

            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/post/defavorite/{favoriteId}")
    public ResponseEntity defavoritePost(@PathVariable("favoriteId") Long favoriteId) {

        try {
            postService.defavoritPost(favoriteId);

            return new ResponseEntity<>(new Result(null, false), HttpStatus.OK);
        } catch (FavoriteNotFoundException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Result(e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
