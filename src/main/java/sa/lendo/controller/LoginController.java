package sa.lendo.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sa.lendo.dto.request.AuthRequest;
import sa.lendo.dto.response.AuthResponse;
import sa.lendo.util.JwtUtil;

@RestController
public class LoginController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        return new AuthResponse(authRequest.getUserName(), jwtUtil.generateToken(authRequest.getUserName()));
    }

}
