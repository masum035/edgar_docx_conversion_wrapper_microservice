package com.wsd.edgardocswrapper.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess == null || resourceAccess.get("edgar-wrapper-api") == null) {
            return Collections.emptyList();
        }

        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("edgar-wrapper-api");

        Collection<String> clientRoles = (Collection<String>) clientAccess.get("roles");

        return clientRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add 'ROLE_' prefix for Spring Security
                .collect(Collectors.toList());
    }
}
