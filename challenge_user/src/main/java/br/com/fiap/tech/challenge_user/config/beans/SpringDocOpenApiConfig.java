package br.com.fiap.tech.challenge_user.config.beans;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI challengeUser() {
        return new OpenAPI()
                .info(new Info()
                        .title("Challenge_user")
                        .description("Microsserviços responsável pelo CRUD de Usuário.")
                        .version("v0.0.3")
                        .termsOfService("http://teste.com.br/terms-of-service") // URL fictícia
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                        .contact(new Contact()
                                .name("Junior Martins")
                                .email("teste@email.com")) // Email fictício
                );
    }
}

