package com.itshow.demo.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor
public class ReplyDto {

    private String content;
    private String memberName;
    private LocalDateTime createdAt;
    private boolean isEdit;
}
