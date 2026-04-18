package alan.nguyen.repository;

import alan.nguyen.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserRepo implements PanacheRepositoryBase<User, UUID> {
}
