package alan.nguyen.controller;


import alan.nguyen.dto.UserDTO;
import alan.nguyen.entity.User;
import alan.nguyen.service.UserService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;


@Path("/users")
public class UserController {

    @Inject
    UserService repo;

    @GET
    @RolesAllowed("ADMIN")
    public Response getAll(){
            List<User> list = repo.getAll();
            return Response.ok(list).build();
    }
    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response updateUser(@PathParam("id") UUID id, UserDTO dto){
            return repo.updateUser(id, dto);
    }

    @POST
    @RolesAllowed({"USER", "ADMIN"})
    public Response addUser(UserDTO dto){
            return repo.addUser(dto);
    }
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deleteUser(UUID id){
            return repo.deleteUser(id);
    }

}

