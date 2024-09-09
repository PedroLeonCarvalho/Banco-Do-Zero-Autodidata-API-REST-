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
                        name = "Pedro Leon",
                        email = "pedrolbbcarvalho@gmail.com"
                ),
                title = "Self-Taught Banking Api from Scratch / Api Rest autodidata do zero",
                description = "Robust REST API, self-taught development, simulates a banking system offering advanced features such as financial transactions, report generation, notification email sending, and savings account interest calculation. Key features include endpoints for file upload/download, JWT authentication, cache storage, automated tests, and MySQL and external Api integration, all documented in Swagger UI.",
                version = "1.0",
                license = @License(name = "Licence (Linkedin)",
                        url = "https://www.linkedin.com/in/pedro-leon-carvalho/"),
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
