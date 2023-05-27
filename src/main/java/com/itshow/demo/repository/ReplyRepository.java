package com.itshow.demo.repository;

import com.itshow.demo.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByPostId(Long post_id);
}
