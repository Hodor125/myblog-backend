package com.hodor.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author ：hodor007
 * @date ：Created in 2021/1/16
 * @description ：
 * @version: 1.0
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器和添加拦截路径以及排除拦截路径
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/logout")
                .excludePathPatterns("/admin/login");
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**");
    }
}
