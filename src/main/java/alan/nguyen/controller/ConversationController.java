package alan.nguyen.controller;

import alan.nguyen.dto.CreateGroupRequestDTO;
import alan.nguyen.service.ConversationService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.awt.*;
import java.util.Map;
import java.util.UUID;

@Path("/api/conversations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class ConversationController {

    @Inject
    ConversationService conversationService;

    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/group")
    public Response createGroupChat(CreateGroupRequestDTO dto){
        UUID creator_id = UUID.fromString(jwt.getSubject());
        try {
            return conversationService.createGroupChat(creator_id, dto.title, dto.memberIds);
        } catch (Exception e) {
            return Response.status(500)
                    .entity(
                            Map.of("message", e.getMessage())
                    )
                    .build();
        }
    }
    @GET
    public Response getMyConversations(){
        UUID myId = UUID.fromString(jwt.getSubject());
        try {
            return Response.ok(conversationService.getUserConversations(myId)).build();
        }catch (Exception e) {
            return Response.status(500)
                    .entity(
                            Map.of("message", e.getMessage())
                    )
                    .build();
        }
    }
}
