package com.itshow.demo.repository.custom;

import com.itshow.demo.dto.PagingDto;
import com.itshow.demo.dto.PostDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    Page pagingPost(Pageable pageable);
}
