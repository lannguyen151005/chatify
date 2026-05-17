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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket(path = "/chat/{conversation_id}")
@Authenticated
public class ChatWebSocket {

    @Inject
    OpenConnections connections;

    @Inject
    UserService userService;

    @Inject
    MessageService messageService;

    @Inject
    MessageReadService msgReadService;

    @Inject
    JsonWebToken jwt;

    @Inject
    ObjectMapper objectMapper;

    // Dùng Map để nhớ connection nào của user nào
    private static final Map<String, String> connectionUserMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(WebSocketConnection connection, @PathParam("conversation_id") String conversation_id){
        String userId = jwt.getSubject();

        // Lưu lại để lúc sau (OnClose) biết ai vừa thoát
        connectionUserMap.put(connection.id(), userId);

        System.out.println("User ["+userId+"] joined the group chat!");

        // 1. Cập nhật trạng thái trong db
        userService.updateUserStatus(UUID.fromString(userId), true);

        // 2. Bắn tín hiệu "Tôi vừa ONLINE" cho những người đang ở trong phòng
        String status_json = "{\"type\":\"STATUS\", \"user_id\":\"" + userId + "\", \"is_online\":true}";
        broadcastToRoom(UUID.fromString(conversation_id), status_json);
    }

    @OnTextMessage
    public void onMessage(MessageRequestDTO request, @PathParam("conversation_id") String conversation_id) throws JsonProcessingException {
        UUID senderId = UUID.fromString(jwt.getSubject());

        if(request.type.equals("TYPING")){
            String typing_json = "{\"type\":\"TYPING\", \"senderId\":\"" + senderId + "\"}";
            broadcastToRoom(UUID.fromString(conversation_id), typing_json);
            return;
        }

        if(request.type.equals("READ")){
            msgReadService.markAsRead(request.message_id, senderId);
            String read_json = "{\"type\":\"READ\", \"userId\":\"" + senderId + "\", \"message_id\":\"" + request.message_id + "\"}";
            broadcastToRoom(UUID.fromString(conversation_id), read_json);
            return;
        }

        Message saved_msg = messageService.sendMessage(senderId, UUID.fromString(conversation_id), request.content, request.attachment_url);
        String json_msg = objectMapper.writeValueAsString(saved_msg);
        broadcastToRoom(UUID.fromString(conversation_id), json_msg);
    }

    @OnClose
    public void onClose(WebSocketConnection connection, @PathParam("conversation_id") String conversation_id){
        // Lấy ID từ Map sẽ an toàn hơn, đề phòng lúc ngắt kết nối JWT bị mất Context
        String user_id = connectionUserMap.getOrDefault(connection.id(), jwt.getSubject());

        System.out.println("User ["+user_id+"] left the group chat ["+conversation_id+"]!");

        if (user_id != null) {
            // 1. Cập nhật trạng thái Offline trong DB
            userService.updateUserStatus(UUID.fromString(user_id), false);

            // 2. Bắn tín hiệu "Tôi vừa OFFLINE" cho những người còn lại
            String status_json = "{\"type\":\"STATUS\", \"user_id\":\"" + user_id + "\", \"is_online\":false}";
            broadcastToRoom(UUID.fromString(conversation_id), status_json);
        }

        // Dọn rác
        connectionUserMap.remove(connection.id());
    }

    private void broadcastToRoom(UUID conversation_id, String json_msg){
        connections.forEach(conn -> {
            // BAO BỌC TRY-CATCH KHỐI NÀY ĐỂ BẢO VỆ SERVER KHỎI MỌI LỖI CRASH
            try {
                if(conversation_id.toString().equals(conn.pathParam("conversation_id"))){
                    conn.sendTextAndAwait(json_msg);
                }
            } catch (Exception e) {
                System.err.println("Bỏ qua kết nối lỗi: " + e.getMessage());
            }
        });
    }
}