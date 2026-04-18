package alan.nguyen.repository;

import alan.nguyen.entity.Conversation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ConversationRepo implements PanacheRepositoryBase<Conversation, UUID> {
    public List<Conversation> getConversations(UUID userId){
        String query = "SELECT c FROM Conversation c JOIN Participant p ON c.id = p.conversation_id WHERE p.user_id = ?1 ORDER BY c.created_at DESC";
        return find(query, userId).list();
    }
}
