package az.company.mssubscription.dao.repository;

import az.company.mssubscription.dao.entity.SubscriptionEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {
    List<SubscriptionEntity> findByExpireDateLessThanEqual(LocalDateTime currentDate);

}