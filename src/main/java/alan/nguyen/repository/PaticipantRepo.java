package alan.nguyen.repository;

import alan.nguyen.entity.Participant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PaticipantRepo implements PanacheRepositoryBase<Participant, UUID> {
}
