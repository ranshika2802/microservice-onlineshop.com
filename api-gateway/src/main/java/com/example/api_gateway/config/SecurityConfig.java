package com.example.api_gateway.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String ROLES_CLAIM = "roles"; // Keycloak role claim

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuerUri;

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.csrf(csrf -> csrf.disable())
        .authorizeExchange(
            exchanges -> exchanges.anyExchange().permitAll()
            //                    .pathMatchers("/eureka/**")
            //                    .permitAll()
            //                    .pathMatchers("/v1/customer/**")
            //                    .hasRole("customer")
            //                    .pathMatchers("/v1/admin/**")
            //                    .hasRole("admin")
            //                    .anyExchange()
            //                    .authenticated()
            )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtDecoder(jwtDecoder())));
    return http.build();
  }

  @Bean
  ReactiveJwtDecoder jwtDecoder() {
    NimbusReactiveJwtDecoder jwtDecoder =
        (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(issuerUri);

    OAuth2TokenValidator<Jwt> withClockSkew =
        new DelegatingOAuth2TokenValidator<>(
            new JwtTimestampValidator(Duration.ofMinutes(120)), new JwtIssuerValidator(issuerUri));

    jwtDecoder.setJwtValidator(withClockSkew);

    return jwtDecoder;
  }
}
