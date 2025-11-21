package br.com.jvbarbosa.store.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("E-commerce Store API")
                .description("API developed for portfolio, featuring inventory and order management.")
                .contact(new Contact().name("João Victor").email("contato.jvb03@gmail.com"))
                .version("1.0.0"));

    }
}
