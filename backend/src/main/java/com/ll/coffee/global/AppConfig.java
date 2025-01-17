package com.ll.coffee.global;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author shjung
 * @since 25. 1. 14.
 */
@Configuration
public class AppConfig {
    @Getter
    private static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        AppConfig.objectMapper = objectMapper;
    }

    public static boolean isNotProd() {
        return false;
    }

    public static String getSiteFrontUrl() {
        return "http://localhost:3000";
    }
}