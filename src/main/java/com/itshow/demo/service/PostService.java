package com.itshow.demo.service;

import com.itshow.demo.common.Util;
import com.itshow.demo.domain.Member;
import com.itshow.demo.domain.Post;
import com.itshow.demo.dto.PagingDto;
import com.itshow.demo.dto.PostDto;
import com.itshow.demo.dto.WritePostDto;
import com.itshow.demo.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PagingDto<List<PostDto>> getPostList(Pageable page) {

        Page<PostDto> paging = postRepository.pagingPost(page);
        return new PagingDto<List<PostDto>>(paging.getContent(),
                paging.getTotalElements(),
                paging.getTotalPages());
    }

    public void writePost(WritePostDto writePostDto) {
        Member loginMember = Util.getLoginMember();
        Post post = Post.builder()
                .title(writePostDto.getTitle())
                .content(writePostDto.getContent())
                .writeBy(loginMember)
                .build();
        postRepository.save(post);
    }
}
