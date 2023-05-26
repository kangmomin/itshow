package com.itshow.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePostDto {

    @NotNull
    private Long id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
