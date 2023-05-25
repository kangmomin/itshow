package com.itshow.demo.config;

import com.itshow.demo.common.Util;
import com.itshow.demo.domain.Member;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuditorAware implements AuditorAware<String> {

    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        Member loginMember = Util.getLoginMember();
        if (loginMember == null) return Optional.empty();
        String loginId = loginMember.getLoginId();

        return Optional.ofNullable(loginId);
    }
}
