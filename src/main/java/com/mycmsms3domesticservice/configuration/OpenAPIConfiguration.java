package com.mycmsms3domesticservice.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "MyCms Domestic Transfer Service",
                description = "Transfer cash using different bank but in same Country",
                version = "1.0.1"
        )
)

@Configuration
public class OpenAPIConfiguration {

}
