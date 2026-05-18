package alan.nguyen.dto;

import alan.nguyen.common.GroupRole;
import lombok.Data;

import java.util.UUID;

public class MemberResponseDTO {
    public UUID user_id;
    public String username;
    public GroupRole group_role;

    public MemberResponseDTO(UUID user_id, String username, GroupRole group_role) {
        this.user_id = user_id;
        this.username = username;
        this.group_role = group_role;
    }
}
