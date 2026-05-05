package alan.nguyen.service;

import alan.nguyen.common.SystemRole;
import alan.nguyen.dto.UserDTO;
import alan.nguyen.entity.User;
import alan.nguyen.repository.UserRepo;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class UserService{

    @Inject
    UserRepo userRepo;

    public List<User> getAll(){
        List<User> list = userRepo.findAll().list();
        return list;
    }
    @Transactional
    public Response addUser(UserDTO dto){
        User existing_user = userRepo.find("email = ?1 or username = ?2", dto.getEmail(), dto.getUsername()).firstResult();
        if(existing_user!=null)
            return Response.status(400)
                    .entity(
                            Map.of("message", "Email or username already exists")
                    )
                    .build();
        User newUser = new User();
        newUser.map(dto);
        userRepo.persist(newUser);
        return Response.ok(
                Map.of("message", "Adding successfully")
        ).build();
    }
    @Transactional
    public Response updateUser(UUID id, UserDTO dto){
        User existing_user = userRepo.findById(id);
        if(existing_user==null)
            return Response.status(404)
                    .entity(
                            Map.of("message", "User not found")
                    )
                    .build();
        User checkEmail_Username = userRepo.find("email = ?1 or username = ?2", dto.getEmail(), dto.getUsername()).firstResult();
        if(checkEmail_Username!=null && !checkEmail_Username.getId().equals(id))
            return Response.status(400)
                    .entity(
                            Map.of("message", "Email or username already exists")
                    )
                    .build();
        if(!isValidRole(dto.getRole()))
            return Response.status(404)
                    .entity(
                            Map.of("message", "Role not found")
                    )
                    .build();

        existing_user.setEmail(dto.getEmail());
        existing_user.setUsername(dto.getUsername());
        existing_user.setPassword(dto.getPassword());
        existing_user.setAvatar_url(dto.getAvatar_url());
        existing_user.set_online(dto.is_online());
        existing_user.setRole(SystemRole.valueOf(dto.getRole()));
        existing_user.setLast_seen(dto.getLast_seen());
        existing_user.setCreated_at(dto.getCreated_at());

        return Response.ok(
                Map.of("message", "Updating successfully")
        ).build();
    }
    @Transactional
    public Response deleteUser(UUID id){
        User existing_user = userRepo.findById(id);
        if(existing_user==null)
            return Response.status(404)
                    .entity(
                            Map.of("message", "User not found")
                    )
                    .build();
        userRepo.delete(existing_user);
        return Response.ok(
                Map.of("message", "Deleting successfully")
        ).build();
    }
    public boolean isValidRole(String role){
        for(SystemRole r : SystemRole.values()){
            if(r.name().equals(role))
                return true;
        }
        return false;
    }
    //Update user'status
    @Transactional
    public void updateUserStatus(UUID userId, boolean isOnline){
        User existing_user = userRepo.findById(userId);
        if(existing_user!=null){
            existing_user.set_online(isOnline);
            if(!isOnline){
                existing_user.setLast_seen(LocalDateTime.now());
            }
            userRepo.persist(existing_user);
        }
    }
}
