package alan.nguyen.controller.auth;

import alan.nguyen.dto.UserDTO;
import alan.nguyen.service.UserService;
import alan.nguyen.service.auth.JwtService;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthController {

    @Inject
    JwtService service;
    @Inject
    UserService user_service;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(UserDTO dto){
        return service.getToken(dto);
    }

    @POST
    @Path("/sign-up")
    @PermitAll
    public Response sign_up(UserDTO dto){
        return user_service.addUser(dto);
    }

}
