package alan.nguyen.websocket;

import alan.nguyen.dto.MessageRequestDTO;
import alan.nguyen.entity.Message;
import alan.nguyen.service.MessageReadService;
import alan.nguyen.service.MessageService;
import alan.nguyen.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.security.Authenticated;
import io.quarkus.websockets.next.*;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@WebSocket(path = "/chat/{conversation_id}")
@Authenticated
public class ChatWebSocket {

    @Inject
    OpenConnections connections;

    @Inject
    UserService userService;

    @Inject
    MessageService msg_service;

    @Inject
    MessageReadService msgReadService;

    @Inject
    JsonWebToken jwt;

    @Inject
    ObjectMapper objectMapper;
    @Inject
    MessageService messageService;

    @OnOpen
    public void onOpen(@PathParam("conversation_id") String conversation_id){
        String userId = jwt.getSubject();
        System.out.println("User ["+userId+"] joined the group chat!");
        userService.updateUserStatus(UUID.fromString(userId), true);
    }

    @OnTextMessage
    public void onMessage(MessageRequestDTO request, @PathParam("conversation_id") String conversation_id) throws JsonProcessingException {
        //Get user id from token
        UUID senderId = UUID.fromString(jwt.getSubject());

        //TYPING
        if(request.type.equals("TYPING")){
            String typing_json = "{\"type\":\"TYPING\", \"senderId\":\"" + senderId + "\"}";
            broadcastToRoom(UUID.fromString(conversation_id), typing_json);
            return;
        }

        //READ
        if(request.type.equals("READ")){
            //mark as read in db
            msgReadService.markAsRead(request.message_id, senderId);

            String read_json = "{\"type\":\"READ\", \"userId\":\"" + senderId + "\", \"message_id\":\"" + request.message_id + "\"}";
            broadcastToRoom(UUID.fromString(conversation_id), read_json);
            return;
        }
        //Save message into database
        Message saved_msg = messageService.sendMessage(senderId, UUID.fromString(conversation_id), request.content, request.attachment_url);

        //Convert saved message into JSON
        String json_msg = objectMapper.writeValueAsString(saved_msg);
        broadcastToRoom(UUID.fromString(conversation_id), json_msg);
    }

    @OnClose
    public void onClose(@PathParam("conversation_id") String conversation_id){
        String user_id = jwt.getSubject();
        System.out.println("User ["+user_id+"] left the group chat ["+conversation_id+"]!");
        userService.updateUserStatus(UUID.fromString(user_id), false);
    }

    private void broadcastToRoom(UUID conversation_id, String json_msg){
        connections.forEach(conn -> {
            if(conversation_id.toString().equals(conn.pathParam("conversation_id"))){
                conn.sendTextAndAwait(json_msg);
            }
        });
    }
}
