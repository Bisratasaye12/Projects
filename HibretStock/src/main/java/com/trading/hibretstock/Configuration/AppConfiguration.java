package com.trading.hibretstock.Configuration;

import com.trading.hibretstock.Utils.HeaderInterceptorUtil;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) 
    {
        registry.addInterceptor(new HeaderInterceptorUtil());
    }

}