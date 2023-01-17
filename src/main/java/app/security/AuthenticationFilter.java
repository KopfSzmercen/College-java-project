package app.security;

import app.services.ApplicationUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtSecurityUtil jwtService;
    private final ApplicationUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = parseBearerToken(request);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        try {
            username = jwtService.getTokenUsername(token);
        } catch (Exception ex) {
            // Likely JWT invalid or expired.
            log.error("Issue with JWT: " + ex.getMessage());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (username != null && auth == null) {
            try {
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (Exception ex) {
                log.error("Issue with JWT: " + ex.getMessage());
            }
           
        }

        chain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        return header.substring(7);
    }
}
