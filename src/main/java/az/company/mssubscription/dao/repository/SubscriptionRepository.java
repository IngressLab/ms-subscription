package az.company.mssubscription.dao.repository;

import az.company.mssubscription.dao.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM subscriptions WHERE expire_date <= NOW()")
    Optional<List<SubscriptionEntity>> findByExpiredSubscriptions();

}
