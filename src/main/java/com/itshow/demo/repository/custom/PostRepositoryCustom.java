package com.itshow.demo.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page pagingPost(Pageable pageable);
}
