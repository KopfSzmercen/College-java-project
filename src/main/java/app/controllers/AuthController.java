package app.controllers;


import app.domain.UserRole;
import app.dto.request.LoginDto;
import app.dto.request.RegisterDto;
import app.dto.response.LoginSuccess;
import app.repositories.ApplicationUserRepository;
import app.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ApplicationUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginSuccess> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authResult = authService.login(loginDto);
        if (!authResult.isAuthenticated()) {
            throw new BadCredentialsException("Email Address or Password is incorrect");
        }

        authService.setAuthenticatedUser(authResult);

        String accessToken = authService.getAccessToken(loginDto.getEmailAddress());
        String greeting = "Hello " + authResult.getName();

        var user = userRepository.findByEmailAddress(loginDto.getEmailAddress()).get();

        return ResponseEntity.ok(new LoginSuccess(greeting, accessToken, user.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        authService.registerTeacher(registerDto, UserRole.TEACHER);
        return ResponseEntity.ok("Success");
    }
}
