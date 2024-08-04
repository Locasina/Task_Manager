package ru.crm.taskboard.security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> responseBody = new HashMap<>();
        final ObjectMapper objectMapper = new ObjectMapper();

        responseBody.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
        responseBody.put("statusMessage", Strings.isNullOrEmpty(authException.getMessage()) ? "Authentication required!" : authException.getMessage());

        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
