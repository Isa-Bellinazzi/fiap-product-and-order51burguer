package com.fiap.burguer.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api()
    {
        return GroupedOpenApi.builder()
                .group("OpenApiController")
                .packagesToScan("com.fiap.burguer")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        List<String> contacts = Arrays.asList(
                "Carlos Jafet  - RM 354076 - cjafet07@gmail.com\n",
                "Guilherme Macedo Moreira - RM 353750 - guilherme.macedomoreira@gmail.com\n",
                "Isabella Bellinazzi da Silva - RM 354143 - isabellinazzi@hotmail.com\n",
                "Juliano Silva Nunes - RM 354144 - silva.juliano8130@gmail.com\n",
                "Thiago Augusto Nery - RM 355063 - doomerbr@gmail.com"
        );

        String contactsDescription = String.join("\n", contacts);

        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("FIAP - Burger API")
                        .contact(new Contact().name("Grupo 51").url("https://github.com/GuiMM/fiap-51burguer"))
                        .version("2.0.0")
                        .description("API for managing burgers\n\nContact:\n\n" + contactsDescription));
    }
}
