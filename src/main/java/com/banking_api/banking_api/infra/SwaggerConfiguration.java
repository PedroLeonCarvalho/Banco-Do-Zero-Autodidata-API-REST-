package com.banking_api.banking_api.infra;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Pedro",
                        email = "pedro@g.com"
                ),
                title = "Banking Api",
                description = "Banking APIS REST aplication provides basic transactions and integrates with external banking api",
                version = "1.0",
                license = @License(name = "License name",
                        url = "http://someurl.com"),
                termsOfService = "Terms of service"
        )
)
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER)

public class SwaggerConfiguration {

}
