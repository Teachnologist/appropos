package com.apropos.crypto.app;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class resourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      /*  registry.addResourceHandler("/style/**")
                .addResourceLocations("/webjars/material-design-lite/1.1.0/");*/

        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


}
