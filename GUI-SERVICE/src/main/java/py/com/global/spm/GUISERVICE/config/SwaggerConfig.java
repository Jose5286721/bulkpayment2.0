package py.com.global.spm.GUISERVICE.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.Arrays;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static String authorization="Authorization";

    @Value("${swagger.security.client-id}")
    private String clienteId;
    @Value("${swagger.security.client-secret}")
    private String clienteSecret;
    @Value("${swagger.security.realm}")
    private String realm;
    @Value("${swagger.security.appName}")
    private String appName;

    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("bulkpayment-api")
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Lists.newArrayList(securityContext()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .build()
                .apiInfo(apiInfo())
                .pathProvider(new RelativePathProvider(servletContext));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("BULKPAYMENT - API ")
                .description("Servicios REST - GUI")
                .contact(contact())
                .version("2.0")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api.*"))
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clienteId)
                .clientSecret(clienteSecret)
                .realm(realm)
                .appName(appName)
                .scopeSeparator(",")
                .additionalQueryStringParams(null)
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }


    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(authorization, authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey(authorization, authorization, "header");
    }


    private Contact contact(){
        return new Contact("Global SI","",
                "christian.delgado@globalsi.com.py");
    }

}
