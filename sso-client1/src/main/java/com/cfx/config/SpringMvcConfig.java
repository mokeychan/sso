package com.cfx.config;

import com.cfx.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * @Description:
 * @Author: chenfeixiang
 * @Date: Created in 15:57 2018/9/28
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer{

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/*");
    }
}
