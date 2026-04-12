package alan.nguyen.repository;

import alan.nguyen.entity.User;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserResourse implements PanacheRepositoryBase<User, UUID> {

    @WithSession
    public Uni<List<User>> getAll(){
        return listAll();
    }

    @WithTransaction
    public Uni<Boolean> save(User user){
        return find("email", user.getEmail()).firstResult()
                .onItem().ifNotNull().transform(existing -> false) // đã tồn tại
                .onItem().ifNull().switchTo(() ->
                        persist(user).replaceWith(true) // chưa tồn tại thì lưu
                );
    }

    @WithTransaction
    public Uni<Boolean> delete(UUID id){
        return findById(id)
                .onItem().ifNotNull().transformToUni(user ->
                        delete(user).replaceWith(true)
                )
                .onItem().ifNull().continueWith(false);
    }

}
