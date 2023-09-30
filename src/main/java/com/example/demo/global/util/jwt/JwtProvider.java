package com.example.demo.global.util.jwt;

import com.example.demo.global.util.JsonUtil;
import com.example.demo.global.util.redis.RedisUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    //    @Autowired
    private final RedisUtil redisUtil;
    private SecretKey cachedSecretKey;

    public final static long ACCESS_TOKEN_VALIDATION_SECOND = (1000L * 60 * 30); // 30분

    public final static long REFRESH_TOKEN_VALIDATION_SECOND = (1000L * 60 * 60) * 24 * 14; // 14일

    @Value("${custom.jwt.secret-key}")
    private String secretKeyPlain;

    private SecretKey _getSecretKey() {
        // 키를 Base64 인코딩함
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        // 인코딩된 키를 이용하여 SecretKey 객체 생성
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    // 가지고 있는 secretKey 가 없으면 생성, 있으면 해당 값 반환
    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }



    // 토큰으로부터 클레임을 가져온다
//    public Map<String, Object> getClaims(String token) {
//        String body = Jwts.parserBuilder()
//                .setSigningKey(getSecretKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("body", String.class);
//
//        return JsonUtil.toMap(body);
//    }

    @Async
    public CompletableFuture<Map<String, Object>> getClaims(String token) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String body = Jwts.parserBuilder()
                        .setSigningKey(getSecretKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .get("body", String.class);

                if (body == null) {
                    // 또는 적절한 예외를 던질 수 있습니다.
                    return Collections.emptyMap();
                }

                return JsonUtil.toMap(body);
            } catch (Exception e) {
                // 로깅하거나 적절한 예외를 던지세요.
                throw new RuntimeException("Error processing JWT", e);
            }
        });
    }

}
