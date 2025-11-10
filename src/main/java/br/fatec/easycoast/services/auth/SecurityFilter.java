package br.fatec.easycoast.services.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    //Filter to validate the Tokens
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{
        String token = recoverToken(request);
        if(token != null){
            //Validate the token and get the User name
            String login = tokenProvider.validateString(token);
            //Get the User
            UserDetails user = userService.loadUserByUsername(login);
            //Authenticate it
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            //Set the authentication to the request
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
      //Get the authorization header
      var authHeader = request.getHeader("Authorization");
      if (authHeader == null)
        return null;
      //Get the token
      return authHeader.replace("Bearer ", "");
    }
}
