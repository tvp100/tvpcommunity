package com.tvp100.community.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Created by tvp100 on 2020/4/5.
 */
@Configuration
public class UploadConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File pathpp = new File("src/main/resources/uploadFiles/");
        registry.addResourceHandler("/uploadFiles/**")
                .addResourceLocations("file:"+pathpp.getAbsolutePath()+File.separator);
    }
}
