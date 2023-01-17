package app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtSecurityUtil {

    public Boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String tokenUser = getTokenUsername(token);
        return tokenUser.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String getTokenUsername(String token) {
        Claims claims = extractClaims(token);

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {
        Claims claims = extractClaims(token);
        return claims.getExpiration();
    }

    public String buildToken(String emailAddress) {
        Map<String, Object> claims = new HashMap<>();

        Date now = new Date(System.currentTimeMillis());
        Date expires = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME_SECS);

        return Jwts.builder().setClaims(claims)
                .setSubject(emailAddress)
                .setIssuedAt(now)
                .setExpiration(expires)
                .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET_KEY)
                .compact();
    }
}
