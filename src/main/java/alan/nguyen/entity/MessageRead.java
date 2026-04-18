package alan.nguyen.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "message_reads")
public class MessageRead extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "message_id")
    private UUID message_id;
    @Column(name = "user_id")
    private UUID user_id;
    @Column(name = "read_at")
    private Date read_at;
}
