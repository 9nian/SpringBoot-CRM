package com.gb.configuration;

import com.gb.crm.setting.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String path [] = {"/settings/**","/workbench/**"};
        String excludePath [] = {"/settings/qx/user/toLogin.do","/settings/qx/user/login.do"};

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(path).excludePathPatterns(excludePath);
    }
}
