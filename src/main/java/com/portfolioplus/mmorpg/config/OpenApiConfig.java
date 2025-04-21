package com.portfolioplus.mmorpg.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI mmorpgApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("MMORPG Character Catalog API")
                        .version("1.0.0")
                        .description("Manage players, characters, and inventory"));
    }

    @Bean
    public GroupedOpenApi allEndpoints() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**")
                .build();
    }
}