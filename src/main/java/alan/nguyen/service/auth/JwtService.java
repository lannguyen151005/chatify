package alan.nguyen.service.auth;

import alan.nguyen.dto.UserDTO;
import alan.nguyen.entity.User;
import alan.nguyen.service.UserService;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Singleton
public class JwtService {

    @Inject
    UserService user_service;

    public String generateJwt(UUID id,String role){

        long duration = System.currentTimeMillis() + 3600;
        return Jwt.issuer("chatify-jwt")
                .subject(id.toString())
                .groups(role)
                .expiresAt(duration)
                .sign();
    }

    public Response getToken(UserDTO dto) {
        User existing_user = user_service.find("username = ?1 and password = ?2", dto.getUsername(), dto.getPassword()).firstResult();
        if(existing_user==null)
            return Response.status(404)
                    .entity(
                            Map.of("message", "Username or password is incorrect.")
                    )
                    .build();
        UUID myId = existing_user.getId();
        String role = existing_user.getRole().toString();
        String token = generateJwt(myId, role);
        return Response.ok(
                Map.of("token", token)
                )
                .build();
    }
}
