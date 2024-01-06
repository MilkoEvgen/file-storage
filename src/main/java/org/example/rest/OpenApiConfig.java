package org.example.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "File Storage Api",
                description = "Description", version = "1.0.0",
                contact = @Contact(
                        name = "Milko Eugene",
                        email = "milllko@mail.ru"
                )
        )
)
public class OpenApiConfig {
}
