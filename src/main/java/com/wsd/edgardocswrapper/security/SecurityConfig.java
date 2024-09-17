package com.wsd.edgardocswrapper.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; script-src 'self'; object-src 'none';")
                        )
                        .xssProtection(HeadersConfigurer.XXssConfig::disable  // XSS protection header is automatically handled by modern browsers; disable it explicitly if not needed
                        )
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny // Prevents clickjacking by blocking all framing
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)  // Sets the HSTS max-age directive
                                .includeSubDomains(true)    // Applies HSTS to all subdomains
                        )
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.NO_REFERRER_WHEN_DOWNGRADE) // Sets the referrer policy
                        )
                        .contentTypeOptions(withDefaults())  // Prevents MIME-type sniffing
                        .permissionsPolicy(permissions -> permissions
                                .policy("geolocation=(self), microphone=(), camera=()") // Controls feature permissions
                        )
                )
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Adjust based on security needs
                )
                .formLogin(withDefaults());

        return http.build();
    }
}
