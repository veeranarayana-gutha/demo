package com.example.demo;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.controller.ApplicationController;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<ApplicationController> customServletRegistration() {
        ServletRegistrationBean<ApplicationController> registration = new ServletRegistrationBean<>(new ApplicationController(), "/app");
        registration.setLoadOnStartup(1);
        return registration;
    }
}