package com.zee.systems.config;


import com.zee.systems.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Oct, 2026
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer( resourceServer ->
                        resourceServer.jwt(Customizer.withDefaults())
                )
                .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())

                // This corresponds to the Client Registration
                .oauth2Client(Customizer.withDefaults());

        return  http.build();
    }


    // helps to know which claim identifier holds the permission(s)
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaim("authorities");
            if (authorities == null) {
                return List.of();
            }
            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        });

        return jwtAuthenticationConverter;
    }


    @Bean
    JwtDecoder jwtDecoder(@Value("${security.jwt.issuer}")String issuer) {


        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withIssuerLocation(issuer).build();
        OAuth2TokenValidator<Jwt> issuerValidator = JwtValidators.createDefaultWithIssuer(issuer);
        AudienceValidator audienceValidator = new AudienceValidator("assistant_mcp_client");
        jwtDecoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(issuerValidator, audienceValidator));
        return jwtDecoder;
    }


    // strictly for connecting to the mcp server using client credentials
    @Bean
    ClientRegistrationRepository clientRegistrationRepository(
            @Value("${security.client-credentials.client-id}")String clientId,
            @Value("${security.client-credentials.client-secret}")String clientSecret,
            @Value("${security.jwt.issuer}")String issuer) {

        ClientRegistration authServerRegistration = ClientRegistrations.fromIssuerLocation(issuer)
                .registrationId(Constants.AUTH_SERVER_ID)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientName(Constants.AUTH_SERVER_ID)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .issuerUri(issuer)
                .scope(Constants.MCP_SCOPE)
                .build();
        return new InMemoryClientRegistrationRepository(authServerRegistration);
    }

}
