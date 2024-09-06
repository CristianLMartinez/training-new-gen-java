package com.globant.trainingnewgen.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        var contact = new Contact();
        contact.setEmail("cristian.lm@globant.com");
        contact.setName("Javastars");

        return new OpenAPI()
                .info(new Info()
                        .title("Grandma's food API")
                        .version("1")
                        .description("API for Training NewGen")
                        .contact(contact)
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

}
