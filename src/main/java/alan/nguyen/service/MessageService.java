package alan.nguyen.service;

import alan.nguyen.entity.Message;
import alan.nguyen.repository.MessageRepo;
import alan.nguyen.repository.ParticipantRepo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class MessageService{

    @Inject
    ParticipantRepo paticipantRepo;

    @Inject
    MessageRepo messageRepo;

    public List<Message> getMessages(UUID conversation_id, UUID user_id, int page, int size){

        //Check: user is a member in this conversation?
        long count = paticipantRepo.count("conversation_id = ?1 and user_id = ?2", conversation_id, user_id);

        if(count==0)
            throw new WebApplicationException("Bạn không có quyền xem tin nhắn của phòng này", Response.Status.FORBIDDEN);

        return messageRepo.find("conversation_id = ?1 order by created_at asc", conversation_id)
                .page(page, size)
                .list();
    }

    @Transactional
    public Message sendMessage(UUID user_id, UUID conversation_id, String content, String attachment_url){

        long count = paticipantRepo.count("conversation_id = ?1 and user_id = ?2", conversation_id, user_id);
        if (count == 0) {
            throw new WebApplicationException("Bạn không thể gửi tin nhắn vào phòng này", Response.Status.FORBIDDEN);
        }

        Message msg = new Message();
        msg.setConversation_id(conversation_id);
        msg.setUser_id(user_id);
        msg.setContent(content);
        msg.setAttachment_url(attachment_url);
        msg.setCreated_at(LocalDateTime.now());

        messageRepo.persist(msg);

        return msg;
    }
}
