package alan.nguyen.repository;

import alan.nguyen.common.GroupRole;
import alan.nguyen.dto.MemberResponseDTO;
import alan.nguyen.entity.Participant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ParticipantRepo implements PanacheRepositoryBase<Participant, UUID> {
    public List<MemberResponseDTO> getMembers(UUID conversationId) {
        String query = "SELECT p.user_id, u.username, p.group_role " +
                "FROM Participant p " +
                "JOIN User u ON p.user_id = u.id " +
                "WHERE p.conversation_id = ?1 " +
                "ORDER BY p.group_role ASC";
        return getEntityManager()
                .createQuery(query, MemberResponseDTO.class)
                .setParameter(1, conversationId)
                .getResultList();
    }

    public long promotingRole(UUID conversationId, UUID userId, GroupRole role) {
        long updated_row = update("group_role = ?1 WHERE conversation_id = ?2 AND user_id = ?3",role, conversationId, userId);
        return updated_row;
    }

    public long kickUser(UUID conversationId, UUID userId) {
        long deleted_row = delete("conversation_id = ?1 AND user_id = ?2", conversationId, userId);

        return deleted_row;
    }
}
