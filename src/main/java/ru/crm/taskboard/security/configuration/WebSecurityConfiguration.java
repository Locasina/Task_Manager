package ru.crm.taskboard.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.crm.taskboard.security.filters.TokenAuthFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/*",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html/*",
            "/webjars/*",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/*",
            "/swagger-ui/*",
            // other public endpoints of your API may be appended to this array
            "/api/auth/*",
            "/api/account/*",
            "/",
            "/favicon.ico",
            "/*/*.json",
            "/*/*.xml",
            "/*/*.properties",
            "/*/*.woff2",
            "/*/*.woff",
            "/*/*.ttf",
            "/*/*.ttc",
            "/*/*.ico",
            "/*/*.bmp",
            "/*/*.png",
            "/*/*.gif",
            "/*/*.svg",
            "/*/*.jpg",
            "/*/*.jpeg",
            "/*/*.html",
            "/*/*.css",
            "/*/*.js"
    };
    @Value("${crm.allowed.origins}")
    private List<String> allowedOrigins;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Origin", "X-Requested-With", "Content-Type", "Accept", "Accept-Encoding",
                "Accept-Language", "Host", "Referer", "Connection", "User-Agent", "Authorization", "sw-useragent", "sw-version",
                "access-control-allow-origin"));
        corsConfiguration.setMaxAge(1800L);
        corsConfiguration.setAllowedOrigins(allowedOrigins);
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors((cors) -> corsConfigurationSource())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(tokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new TokenAuthenticationEntryPoint())
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.INTERNAL_SERVER_ERROR),
                                new AntPathRequestMatcher("/**"))
                );

        return httpSecurity.build();
    }
}
