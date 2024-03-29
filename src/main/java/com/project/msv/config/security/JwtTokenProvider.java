package com.project.msv.config.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
public class JwtTokenProvider {
    private static final String JWT_SECRET = Base64.getEncoder().encodeToString(
            "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918b167a9c873fc4bb88c6976e5b5410415bde908bd4dee15dfa81f6f2ab448a918"
                    .getBytes());

    // 토큰 유효시간 24시간 (리프레시)
    private static final long JWT_EXPIRATION_REFRESH = 1000L * 60 * 60 * 24 * 60;
    // 토큰 유효시간 60분 (액세스)
    private static final long JWT_EXPIRATION_ACCESS = 1000 * 60 * 60;

    public static enum TokenType {
        REFRESH, ACCESS
    }

    ;

    // jwt 토큰 생성
    public static String generateToken(Long idx, TokenType tokenType) {

        Date now = new Date();
        Date expiryDate;
        switch (tokenType) {
            case REFRESH:
                expiryDate = new Date(now.getTime() + JWT_EXPIRATION_REFRESH);
                break;
            case ACCESS:
                expiryDate = new Date(now.getTime() + JWT_EXPIRATION_ACCESS);
                break;
            default:
                throw new UnsupportedJwtException("Token Type이 없습니다.");
        }

        Map claim = new HashMap();
        claim.put("idx", idx);

        return Jwts.builder()
                .setClaims(claim) // 사용자
                .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                .setExpiration(expiryDate) // 만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                .compact();
    }

    // Jwt 토큰에서 아이디 추출
    public static Object getValueFromJWT(String token, String key) {
        String payload = token.split("[.]")[1];
        JacksonJsonParser parser = new JacksonJsonParser();
        Map map = parser.parseMap(new String(Base64.getDecoder().decode(payload)));
        return map.get(key);
    }

    // Jwt 토큰 유효성 검사
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public static String getJwtFromRequest(HttpServletRequest request, String type) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length < 1) {
            return null;
        }

        Optional<String> token = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(type))
                .map(c -> c.getValue())
                .findFirst();

        return token.isPresent() ? token.get() : null;
    }
}