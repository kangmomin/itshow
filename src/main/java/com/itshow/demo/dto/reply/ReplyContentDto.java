package com.itshow.demo.dto.reply;

import lombok.*;

@Data @AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyContentDto {
    private String content;
}
