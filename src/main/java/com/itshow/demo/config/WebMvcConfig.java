package com.itshow.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        WebMvcConfigurer.super.configureViewResolvers(registry);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        String ALLOW_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
        registry.addMapping("/**")
                .allowedOrigins("*")
//                .allowCredentials(true)
                .exposedHeaders("X-Auth-Token", "X-Refresh-Token")
                .allowedMethods(ALLOW_METHOD_NAMES.split(","));
    }
}
