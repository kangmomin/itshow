package com.itshow.demo.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDto<T> {

    private T entity;
    private long totalPost;
    private long totalPage;
}
