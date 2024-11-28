package com.daretodo.keyboardnotifier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile({"dev", "prod"})
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String corsList;

    public WebConfig(@Value("${app.cors}") String corsList) {
        this.corsList = corsList;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(parseCorsList(corsList))
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

    private static String[] parseCorsList(String corsList) {
        String[] corsOrigins = corsList.split(",");
        for (int i = 0; i < corsOrigins.length; i++) {
            corsOrigins[i] = corsOrigins[i].strip();
        }
        return corsOrigins;
    }
}
