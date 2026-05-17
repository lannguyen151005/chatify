package alan.nguyen.repository;

import alan.nguyen.entity.Conversation;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ConversationRepo implements PanacheRepositoryBase<Conversation, UUID> {
    public List<Conversation> getConversations(UUID userId){
        String query = "SELECT c FROM Conversation c " +
                "JOIN Participant p ON c.id = p.conversation_id " +
                "WHERE p.user_id = ?1 ORDER BY c.created_at DESC";
        return find(query, userId).list();
    }

    public List<UUID> getOnlineUsersId(UUID conversationId) {
        // Dùng Tên Entity (User, Participant) và thuộc tính Java (user_id, is_online)
        String query = "SELECT u.id FROM User u " +
                "JOIN Participant p ON u.id = p.user_id " +
                "WHERE p.conversation_id = ?1 AND u.is_online = true";

        // Dùng EntityManager để ép kiểu kết quả về thẳng List<UUID>
        return getEntityManager()
                .createQuery(query, UUID.class)
                .setParameter(1, conversationId)
                .getResultList();
    }
}
