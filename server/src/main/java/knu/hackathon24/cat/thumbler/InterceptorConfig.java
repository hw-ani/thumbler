package knu.hackathon24.cat.thumbler;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import knu.hackathon24.cat.thumbler.interceptor.AuthCheckInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer{
    final private AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/store/near")
            .excludePathPatterns("/member/**", "/member/users/register", "/member/owner/register"
            ,"/member/user/login", "/member/owner/login");
    }
}
