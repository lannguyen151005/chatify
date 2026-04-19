package alan.nguyen.websocket;

import alan.nguyen.dto.MessageRequestDTO;
import alan.nguyen.entity.Message;
import alan.nguyen.service.MessageService;
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
    MessageService msg_service;

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
    }

    @OnTextMessage
    public void onMessage(MessageRequestDTO request, @PathParam("conversation_id") String conversation_id) throws JsonProcessingException {
        //Get user id from token
        UUID senderId = UUID.fromString(jwt.getSubject());

        //Save message into database
        Message saved_msg = messageService.sendMessage(senderId, UUID.fromString(conversation_id), request.content);

        //Convert saved message into JSON
        String json_msg = objectMapper.writeValueAsString(saved_msg);

        //get all connections to the conversation
        connections.forEach(conn -> {
            //Get the value of conversation_id on URL when connecting
            String targetConversationId = conn.pathParam("conversation_id");

            //if this connection belongs to the true conversation, do messages loading
            if(conversation_id.equals(targetConversationId)){
                conn.sendTextAndAwait(json_msg);
            }
        });
    }

    @OnClose
    public void onClose(@PathParam("conversation_id") String conversation_id){
        String user_id = jwt.getSubject();
        System.out.println("User ["+user_id+"] left the group chat ["+conversation_id+"]!");
    }
}
