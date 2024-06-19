package br.com.jherrerocavadas.saeapi.usuarios.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${application.version}")
    private String applicationVersion;
    @Bean
    public OpenAPI apiConfig() {
        return new OpenAPI()
                .info(new Info().title("SAE API Usuarios")
                        .description("API de usuários para o sistema SAE")
                        .version(applicationVersion)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Jherrerocavadas Github")
                        .url("https://github.com/JHerrerocavadas"));
    }
}
