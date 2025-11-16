package br.fatec.easycoast.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig {
  @Value("${auth.disabled}")
  private boolean authDisabled;

  @Autowired private SecurityFilter securityFilter;

  @Autowired private InitializationFilter initializationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    if (!authDisabled) {
      return httpSecurity
          .csrf(csrf -> csrf.disable())
          .sessionManagement(
              session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          // Managing the Role authorization of the endpoints
          .authorizeHttpRequests(
              authorize ->
                  authorize
                      // Auth
                      .requestMatchers("/auth/**")
                      .permitAll()
                      // Server Status
                      .requestMatchers("/actuator/**")
                      .permitAll()
                      // Documentation
                      .requestMatchers("/scalar/**")
                      .permitAll()
                      .requestMatchers("/api-docs/**")
                      .permitAll()
                      // Restaurant
                      .requestMatchers(HttpMethod.GET, "/restaurant")
                      .permitAll()
                      .requestMatchers("/restaurant/**")
                      .hasRole("ADMIN")
                      // Images
                      .requestMatchers("/images/**")
                      .permitAll()
                      // Customer
                      .requestMatchers(HttpMethod.POST, "/customers")
                      .permitAll()
                      .requestMatchers(HttpMethod.GET, "/customers")
                      .denyAll()
                      .requestMatchers(HttpMethod.GET, "/customers/self")
                      .hasRole("CUSTOMER")
                      .requestMatchers(HttpMethod.GET, "/customers/{id}")
                      .hasRole("CUSTOMER")
                      .requestMatchers(HttpMethod.PUT, "/customers/{id}")
                      .hasRole("CUSTOMER")
                      .requestMatchers(HttpMethod.DELETE, "/customers/{id}")
                      .hasRole("CUSTOMER")
                      // Seats
                      .requestMatchers(HttpMethod.GET, "/seats/**")
                      .permitAll()
                      .requestMatchers(HttpMethod.POST, "/seats")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/seats/{id}")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/seats/**")
                      .hasRole("ADMIN")
                      // Employees
                      .requestMatchers(HttpMethod.GET, "/employees/owner")
                      .hasRole("OWNER")
                      .requestMatchers(HttpMethod.GET, "/employees")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.GET, "/employees/{id}")
                      .hasAnyRole("ADMIN", "POS", "WAITER")
                      .requestMatchers(HttpMethod.POST, "/employees")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/employees/owner")
                      .hasRole("OWNER")
                      .requestMatchers(HttpMethod.PUT, "/employees/{id}")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.DELETE, "/employees/{id}")
                      .hasRole("ADMIN")
                      // Cards
                      .requestMatchers(HttpMethod.GET, "/cards/**")
                      .hasAnyRole("ADMIN", "WAITER", "POS")
                      .requestMatchers(HttpMethod.POST, "/cards")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/cards/**")
                      .hasRole("ADMIN")
                      .requestMatchers(HttpMethod.PUT, "/cards/{id}")
                      .hasAnyRole("ADMIN", "WAITER", "POS")
                      // Squares
                      .requestMatchers("/square/**")
                      .hasRole("ADMIN")
                      // Categories
                      .requestMatchers(HttpMethod.GET, "/categories/**")
                      .permitAll()
                      .requestMatchers("/categories/**")
                      .hasRole("ADMIN")
                      // Subcategories
                      .requestMatchers(HttpMethod.GET, "/subcategories/**")
                      .permitAll()
                      .requestMatchers("/subcategories/**")
                      .hasRole("ADMIN")
                      // Schedules
                      .requestMatchers(HttpMethod.GET, "/schedules")
                      .hasAnyRole("CUSTOMER", "ADMIN", "WAITER", "POS")
                      .requestMatchers(HttpMethod.GET, "/schedules/{id}")
                      .hasAnyRole("CUSTOMER", "ADMIN", "WAITER", "POS")
                      .requestMatchers(HttpMethod.POST, "/schedules")
                      .hasRole("CUSTOMER")
                      .requestMatchers(HttpMethod.PUT, "/schedules/{id}")
                      .hasRole("CUSTOMER")
                      // Items
                      .requestMatchers("/items/**")
                      .hasRole("ADMIN")
                      // Products
                      .requestMatchers(HttpMethod.GET, "/products/**")
                      .permitAll()
                      .requestMatchers("/products/**")
                      .hasRole("ADMIN")
                      // AddonCategories
                      .requestMatchers(HttpMethod.GET, "/addon-categories/**")
                      .permitAll()
                      .requestMatchers("/addon-categories/**")
                      .hasRole("ADMIN")
                      // Addons
                      .requestMatchers(HttpMethod.GET, "/addons/**")
                      .permitAll()
                      .requestMatchers("/addons/**")
                      .hasRole("ADMIN")
                      // Orders
                      .requestMatchers("/orders/**")
                      .hasAnyRole("WAITER", "POS")
                      // OrderItems
                      .requestMatchers("/order-items/**")
                      .hasAnyRole("WAITER", "POS")
                      // Checkouts
                      .requestMatchers("/checkouts/**")
                      .hasRole("POS")
                      // Payments
                      .requestMatchers("/payments/**")
                      .hasRole("POS")
                      .anyRequest()
                      .authenticated())
          .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
          .addFilterBefore(initializationFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
    } else {
      return httpSecurity
          .csrf(csrf -> csrf.disable())
          .sessionManagement(
              session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
          .build();
    }
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RoleHierarchy roleHierarchy() {
    // Setting the role hierarchy
    String hierarchy =
        "ROLE_OWNER > ROLE_ADMIN \n" + "ROLE_ADMIN > ROLE_POS \n" + "ROLE_ADMIN > ROLE_WAITER";
    RoleHierarchy roleHierarchy = RoleHierarchyImpl.fromHierarchy(hierarchy);
    return roleHierarchy;
  }

  @Bean
  public DefaultWebSecurityExpressionHandler expressionHandler() {
    DefaultWebSecurityExpressionHandler expressionHandler =
        new DefaultWebSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy());
    return expressionHandler;
  }
}
