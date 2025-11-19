package br.fatec.easycoast.services.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.fatec.easycoast.services.InitializationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class InitializationFilter extends OncePerRequestFilter {
    
    @Autowired
    private InitializationService initializationService;
    
    private static final List<String> ALLOWED_PATHS_DURING_INIT = Arrays.asList(
        "/auth/sign-in",
        "/auth/status",
        "/employees/owner",
        "/actuator",
        "/scalar",
        "/api-docs"
    );
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        
        String requestPath = request.getRequestURI();
        
        if (!initializationService.isOwnerInitialized()) {
            boolean isAllowedPath = requestPath.equals("/") || ALLOWED_PATHS_DURING_INIT.stream()
                                                                        .anyMatch(requestPath::startsWith);
                
            if (!isAllowedPath) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"System not initialized. Please complete owner setup first.\"}");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }
}