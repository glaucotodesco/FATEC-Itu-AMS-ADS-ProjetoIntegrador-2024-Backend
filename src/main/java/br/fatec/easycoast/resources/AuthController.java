package br.fatec.easycoast.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.fatec.easycoast.dtos.auth.SignIn;
import br.fatec.easycoast.services.InitializationService;
import br.fatec.easycoast.services.auth.TokenProvider;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private InitializationService initializationService;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody SignIn user){
        UsernamePasswordAuthenticationToken userPwd = new UsernamePasswordAuthenticationToken(user.login(), user.password());
        var auth = authenticationManager.authenticate(userPwd);
        var token = tokenProvider.generateAccessToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }
    
    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getInitializationStatus() {
        return ResponseEntity.ok(Map.of("initialized", initializationService.isOwnerInitialized()));
    }
}
