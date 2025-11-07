package br.fatec.easycoast.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
  @Value("${easycoast.frontend-origins}")
  private String[] origins;

  @Bean
  public WebMvcConfigurer corsConfigurer () {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings (CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(origins).allowedMethods("*");
      }
    };
  }

}
