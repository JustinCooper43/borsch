package com.nayax.borsch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static java.lang.String.format;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private String appName;
    private String title;
    private String description;
    private String version;
    private String basePackage;
    public SwaggerConfig(  @Value("${swagger.config.appName}") String appName,
                           @Value("${swagger.config.title}") String title,
                           @Value("${swagger.config.description}") String description,
                           @Value("${swagger.config.basePackage}") String basePackage,
                           @Value("${swagger.config.version}") String version){

        this.appName = appName;
        this.title = title;
        this.description = description;
        this.version = version;
        this.basePackage = basePackage;
    }

    @Bean
    public Docket createApiDocket() {

        var produceType = format(appName, version);

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(format("core-api-%s", version))
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .build()
                .forCodeGeneration(true)
                .produces(Collections.singleton(produceType))
                .apiInfo(new ApiInfoBuilder()
                        .version(version)
                        .title(title)
                        .description(format(description, version))
                        .build());
    }


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Allow anyone and anything access. Probably ok for Swagger spec
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/v2/api-docs", config);
        return new CorsFilter(source);
    }
}
