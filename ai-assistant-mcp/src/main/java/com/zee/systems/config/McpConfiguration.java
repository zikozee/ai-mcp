package com.zee.systems.config;


import com.zee.systems.constants.Constants;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.transport.customizer.McpSyncHttpClientRequestCustomizer;
import org.jspecify.annotations.NonNull;
import org.springaicommunity.mcp.security.client.sync.AuthenticationMcpTransportContextProvider;
import org.springaicommunity.mcp.security.client.sync.oauth2.http.client.OAuth2ClientCredentialsSyncHttpRequestCustomizer;
import org.springframework.ai.mcp.customizer.McpClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Set;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Oct, 2026
 */

@Configuration
public class McpConfiguration {

    @Bean
    McpClientCustomizer<McpClient.SyncSpec> syncClientCustomizer() {

        return (name, syncSpec) ->
                syncSpec.transportContextProvider(
                        new AuthenticationMcpTransportContextProvider()
                );
    }

    @Bean
    McpSyncHttpClientRequestCustomizer requestCustomizer(AuthorizedClientServiceOAuth2AuthorizedClientManager manager) {

        return new OAuth2ClientCredentialsSyncHttpRequestCustomizer(
                manager,
                Constants.AUTH_SERVER_ID); // "authserver"
    }


    /**
     * This caters for the ApiResponse Dto by destructuring it before setting its values in the OAuth2AccessTokenResponse
     * */
    @Bean
    AuthorizedClientServiceOAuth2AuthorizedClientManager mcpClientCredentialsManager(
            ClientRegistrationRepository registrations,
            OAuth2AuthorizedClientService authorizedClients) {

        OAuth2AccessTokenResponseHttpMessageConverter tokenMessageConverter = createTokenMessageConverter();

        RestClient tokenRestClient = RestClient.builder()
                .configureMessageConverters(converters ->
                        converters.disableDefaults()
                                .addCustomConverter(new FormHttpMessageConverter())
                                .addCustomConverter(tokenMessageConverter)
                )
                .defaultStatusHandler(new OAuth2ErrorResponseErrorHandler())
                .build();

        RestClientClientCredentialsTokenResponseClient tokenResponseClient =
                new RestClientClientCredentialsTokenResponseClient();

        tokenResponseClient.setRestClient(tokenRestClient);

        ClientCredentialsOAuth2AuthorizedClientProvider provider = new ClientCredentialsOAuth2AuthorizedClientProvider();
        provider.setAccessTokenResponseClient(tokenResponseClient);

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(registrations, authorizedClients);
        manager.setAuthorizedClientProvider(provider);

        return manager;
    }

    private static @NonNull OAuth2AccessTokenResponseHttpMessageConverter createTokenMessageConverter() {
        OAuth2AccessTokenResponseHttpMessageConverter tokenMessageConverter
                = new OAuth2AccessTokenResponseHttpMessageConverter();

        tokenMessageConverter.setAccessTokenResponseConverter(body -> {
            Object dataValue = body.get("data");
            if (!(dataValue instanceof Map<?, ?> data)) {
                throw new IllegalArgumentException(
                        "Token response is missing the data object");
            }

            Object tokenValue = data.get("accessToken");
            Object typeValue = data.get("tokenType");
            Object expiresValue = data.get("expiresIn");

            if (!(tokenValue instanceof String token) || token.isBlank()
                    || !(typeValue instanceof String tokenType)
                    || !tokenType.equalsIgnoreCase("Bearer")
                    || !(expiresValue instanceof Number expiresIn)) {
                throw new IllegalArgumentException(
                        "Token response data must contain a Bearer accessToken and numeric expiresIn");
            }

            return OAuth2AccessTokenResponse.withToken(token)
                    .tokenType(OAuth2AccessToken.TokenType.BEARER)
                    .expiresIn(expiresIn.longValue())
                    .scopes(Set.of(Constants.MCP_SCOPE))
                    .build();
        });
        return tokenMessageConverter;
    }



}
