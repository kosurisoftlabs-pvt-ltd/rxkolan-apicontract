package com.kosuri.rxkolan.security;

import com.kosuri.rxkolan.config.AppProperties;
import com.kosuri.rxkolan.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@Slf4j
public class TokenProvider {

    @Autowired
    private AppProperties appProperties;

    @Value("${jwt.validity}")
    private int jwtValidityInHrs;

    @Value("${jwt.secret}")
    private String secret;


    public String getToken(User userPrincipal) {
        return getToken(userPrincipal, appProperties.getAuth().getTokenExpirationMSec());
    }

    public String getToken(User userPrincipal,long expiryMSec) {
        return getToken(Long.valueOf(userPrincipal.getPhoneNumber()),userPrincipal.getUsername(), userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList(), expiryMSec);
    }

    public String getToken(Long id,  String userName, Collection<String> roles) {
        return getToken(id, userName, roles, TimeUnit.HOURS.toMillis(jwtValidityInHrs));
    }

    public String getToken(Long id, String userName, Collection<String> roles, long expiryMSec) {
        return getTokenWithKey(id, userName, roles, expiryMSec);
    }

    public String getTokenWithKey(Long id,String userName, Collection<String> roles, long expiryMSec) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiryMSec);
        Claims claims = Jwts.claims().setSubject(Long.toString(id)).setIssuedAt(new Date()).setExpiration(expiryDate);
        claims.put("roles", roles);

        return Jwts.builder().setClaims(claims).setSubject(userName).signWith(getSignInKey(), SignatureAlgorithm.HS256).setId(userName).compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public Optional<String> getJTIKeyFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

        return Optional.of(claims.getId());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(authToken);
            return true;
        }  catch (ExpiredJwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (Exception ex) {
            log.error("Error occurred while parsing token", ex);
        }
        return false;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

}