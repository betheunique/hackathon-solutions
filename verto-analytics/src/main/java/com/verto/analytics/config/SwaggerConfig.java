package com.verto.analytics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @author abhishekrai
 * @since 26/06/2017
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket analyticsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.verto.analytics.controller"))
                .paths(regex(".*"))
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return  new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Verto Analytics",
                "1.0",
                "Terms of service",
                new Contact("Abhishekkumar Rai", "http://www.vertoanalytics.com", "abhisheksavitarai@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }
}
