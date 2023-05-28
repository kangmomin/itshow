package com.itshow.demo.common;

import com.itshow.demo.config.security.PrincipalDetails;
import com.itshow.demo.domain.Member;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
    public static Member getLoginMember() {
        try {
            PrincipalDetails member = (PrincipalDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            return member.getMember();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * xss 공격 방어
     * @param content
     * @return escaped content
     */
    public static String escaper(String content) {
        return content.replace("<", "&lt;")
                .replace(">", "&gt");
    }
}
