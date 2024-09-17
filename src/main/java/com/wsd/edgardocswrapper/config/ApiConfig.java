package com.wsd.edgardocswrapper.config;

import com.wsd.edgardocswrapper.interceptors.CorrelationIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {

    @Autowired
    private CorrelationIdInterceptor correlationIdInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(correlationIdInterceptor);
    }

    
}
