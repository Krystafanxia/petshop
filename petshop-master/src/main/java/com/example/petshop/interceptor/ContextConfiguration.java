package com.example.petshop.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class ContextConfiguration implements WebMvcConfigurer {

    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/manage/**")
                .addPathPatterns("/user/**")
                .addPathPatterns("/upload");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedHeaders("*")
                .allowedMethods("*").allowCredentials(true);
    }
}
