package ru.crm.taskboard.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class SpringdocConfiguration {

    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Constructions backend application REST API")
                        .description("Backend API for Constructions application")
                        .version("0.0.1-ALPHA"));
    }
}
