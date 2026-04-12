package alan.nguyen.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private SystemRoles role = SystemRoles.USER;
    @Column(name = "avatar_url")
    private String avatar_url;
    @Column(name = "is_online")
    private boolean is_online;
    @Column(name = "last_seen")
    private Date last_seen;
    @Column(name = "created_at")
    private Date created_at;

    public enum SystemRoles{
        USER, ADMIN
    }
}
