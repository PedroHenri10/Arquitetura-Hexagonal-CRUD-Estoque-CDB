package com.cdb.estoque.infrastructure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customApiConfig(){
        return new OpenAPI()
            .info(new Info()
                    .title("API Estoque de Games")
                    .version("v1.0")
                    .description("API para gerenciamento de estoque de games, permitindo operações CRUD e controle de quantidade.")
                    .contact(new Contact()
                            .name("Pedro Henrique")
                            .email("dinhonoliver@gmail.com"))
            );
    }
}
