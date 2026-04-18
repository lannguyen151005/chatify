package alan.nguyen.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String avatar_url;
    private boolean is_online;
    private Date last_seen;
    private Date created_at;
    private String role;
}
