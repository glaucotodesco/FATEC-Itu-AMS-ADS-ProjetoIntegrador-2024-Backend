package br.fatec.easycoast.services.auth;

import br.fatec.easycoast.entities.Employee;
import br.fatec.easycoast.services.exceptions.DatabaseException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {
  // Secret Key
  @Value("${security.jwt.token.secret-key}")
  private String JwtSecret;

  public String generateAccessToken(UserDetails user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(JwtSecret);
      return JWT.create()
          // Setting the User of the Token
          .withSubject(user.getUsername())
          .withClaim("username", user.getUsername())
          .withClaim("id", ((Employee) user).getId())
          .withClaim("profile", user.getAuthorities().stream().map(a -> a.getAuthority()).toList())
          // Setting Experation Date
          .withExpiresAt(genAccessExpirationDate())
          // Finish creating the Token
          .sign(algorithm);
    } catch (JWTCreationException e) {
      throw new DatabaseException("Error while generating token!");
    }
  }

  public String validateString(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(JwtSecret);
      // Build the JWT algorithm
      return JWT.require(algorithm)
          .build()
          // Verify if the Token is valid
          .verify(token)
          // Get the User name
          .getSubject();
    } catch (JWTVerificationException e) {
      throw new IllegalArgumentException("Error while validating token!");
    }
  }

  private Instant genAccessExpirationDate() {
    return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
  }
}
