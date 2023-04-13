package com.kosuri.rxkolan.config;

import com.kosuri.rxkolan.constant.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.kosuri.rxkolan.constant.Constants.BEARER_KEY;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(BEARER_KEY,
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer")
                                        .bearerFormat("JWT"))
                        .addParameters(Constants.CLIENT_HEADER,
                                new HeaderParameter().required(false).name(Constants.CLIENT_HEADER)
                                        .description("Client header for recaptcha").schema(new StringSchema())));

    }

    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }
}