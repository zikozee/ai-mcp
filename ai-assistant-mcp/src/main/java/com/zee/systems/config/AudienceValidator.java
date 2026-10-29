package com.zee.systems.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * @dev : Ezekiel Eromosei
 * @date : 29 Sep, 2026
 */

@Slf4j
public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
    private final String audience;

    public AudienceValidator(String audience) {
        this.audience = audience;
    }

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if(jwt.getAudience().contains(audience)) {
            return OAuth2TokenValidatorResult.success();
        } else {
            log.info("Audience: '{}' not available in provided token", audience);
            return OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token",
                            "Token is not intended for this resource server",
                            null
                    )
            );
        }
    }
}
