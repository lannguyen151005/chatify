package alan.nguyen.service.auth;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class JwtService {

    public String generateJwt(){
        Set<String> roles = new HashSet<>(
                Arrays.asList("ADMIN", "USER")
        );
        long duration = System.currentTimeMillis() + 3600;
        return Jwt.issuer("chatify-jwt")
                .subject("chatify-jwt")
                .groups(roles)
                .expiresAt(duration)
                .sign();
    }
}
