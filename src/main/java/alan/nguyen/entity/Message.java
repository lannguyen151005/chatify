package alan.nguyen.entity;

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
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "conversation_id")
    private UUID conversation_id;
    @Column(name = "user_id")
    private UUID user_id;
    @Column(name = "content")
    private String content;
    @Column(name = "attachment_url")
    private String attachment_url;
    @Column(name = "created_at")
    private Date created_at;

}
