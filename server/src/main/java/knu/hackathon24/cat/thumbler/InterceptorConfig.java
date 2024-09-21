package knu.hackathon24.cat.thumbler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import knu.hackathon24.cat.thumbler.interceptor.AuthCheckInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthCheckInterceptor())
        .order(1)
        .addPathPatterns("/**")
        .excludePathPatterns("/store/near", "/member/*/register", "/member/login")
        ;
    }
}
