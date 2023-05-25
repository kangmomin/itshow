package com.itshow.demo.service;

import com.itshow.demo.dto.PagingDto;
import com.itshow.demo.dto.PostDto;
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
}
