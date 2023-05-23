package com.itshow.demo.dto;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

    @Size(min = 5, max = 30)
    private String loginId;

    @Size(min = 8, max = 30)
    private String password;
}
