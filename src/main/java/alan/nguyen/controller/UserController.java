package alan.nguyen.controller;

import alan.nguyen.entity.User;
import alan.nguyen.repository.UserResourse;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/users")
public class UserController {

    @Inject
    UserResourse repo;

    @GET
    public Uni<List<User>> getAll(){
        return repo.getAll(); // ✅ đúng
    }

    @POST
    public Uni<Boolean> add(User user){
        return repo.save(user); // ✅ đúng
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> delete(UUID id){
        return repo.delete(id); // ✅ đúng
    }
}

