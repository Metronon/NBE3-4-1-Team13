package com.ll.coffee.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shjung
 * @since 25. 1. 17.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "API 서버", version = "v1"))
public class SpringDocConfig {


    @Bean
    public GroupedOpenApi groupController() {
        return GroupedOpenApi.builder()
                .group("controller")
                .pathsToMatch("/**")
                .build();
    }
}
