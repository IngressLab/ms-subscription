package az.company.mssubscription.dao.entity;

import az.company.mssubscription.enums.SubscriptionStatus;
import az.company.mssubscription.enums.SubscriptionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscriptions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long userId;
    Long productId;
    Long cardId;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    SubscriptionType subscriptionType;
    @Enumerated(EnumType.STRING)
    SubscriptionStatus status;
    LocalDateTime expireDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionEntity that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
