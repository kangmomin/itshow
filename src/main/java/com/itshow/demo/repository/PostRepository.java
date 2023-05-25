package com.itshow.demo.repository;

import com.itshow.demo.domain.Post;
import com.itshow.demo.repository.custom.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

}
