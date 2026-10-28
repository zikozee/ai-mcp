package com.zee.systems.config;


import org.springaicommunity.mcp.security.server.config.McpServerOAuth2Configurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @dev : Ezekiel Eromosei
 * @date : 27 Oct, 2026
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${security.jwt.issuer}")String issuer) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/mcp").permitAll(); //secure only tool calls while leaving other MCP operations (like initialize and tools/list) public
                    auth.anyRequest().authenticated();
                })
                .with(McpServerOAuth2Configurer.mcpServerOAuth2(), mcpAuthorization -> {

                    mcpAuthorization.authorizationServer(issuer);

                    // defaults to /mcp, hence baseUrl/mcp must be passed as the audience i.e http://localhost:8084/mcp
//                    mcpAuthorization.resourcePath();

                    mcpAuthorization.validateAudienceClaim(true); //validates::  token aud claim contains http://localhost:8084/mcp, configure client audience to contain this

                    // we could choose to create a jwt for the resourceServer so validate the app resource id e.g hr-service-mcp-server
                    // but that means both the mcp ResourceIdentifier   and the hr-service-mcp-server would both be validated
                    mcpAuthorization.oauth2ResourceServer(Customizer.withDefaults());
                })
                .build();
    }


    //configures where to find permissions
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaim("authorities");
            assert authorities != null;
            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        });

        return jwtAuthenticationConverter;
    }


    @Primary
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")  // Allow all origins
                        .allowedMethods(
                                HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                                HttpMethod.PATCH.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()
                        )
                        .allowedHeaders("*")
                        .exposedHeaders("Set-Cookie")
                        .allowCredentials(false)  // Must be false when allowedOrigins is "*"
                        .maxAge(3600);  // Cache the preflight response for 1 hour
            }
        };
    }
}
