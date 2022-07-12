package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer { // Класс, который содержит конфигурацию веб-слоя
    @Value("${upload.path}")
    private String uploadPath;

    @Bean
    public RestTemplate getRestTemplate() {
        return  new RestTemplate();
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login"); // Spring предоставляет систему авторизации
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") // Каждое обращение к серверу по пути /img/**
                .addResourceLocations("file:///" + uploadPath + "/"); // перенаправляет все запросы по пути
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/"); // При обращении по этому пути ресурсы ищутся в дереве проекта
    }
}