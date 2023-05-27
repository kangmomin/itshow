package com.itshow.demo.dto.post;

import com.itshow.demo.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor
public class PostDto {

    private String writerName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
