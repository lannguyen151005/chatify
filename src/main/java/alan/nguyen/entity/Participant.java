package alan.nguyen.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "participants")
public class Participant extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "conversation_id")
    private UUID conversation_id;
    @Column(name = "user_id")
    private UUID user_id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private GroupRoles role = GroupRoles.MEMBER;

    public enum GroupRoles{
        MEMBER, MODERATOR, ADMIN
    }
}
