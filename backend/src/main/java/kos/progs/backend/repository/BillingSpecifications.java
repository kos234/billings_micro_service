package kos.progs.backend.repository;

import jakarta.persistence.criteria.ListJoin;
import kos.progs.backend.consts.BillingStates;
import kos.progs.backend.entity.Billing;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public class BillingSpecifications {

    public static Specification<Billing> hasUserId(Integer userId) {
        return (root, query, cb) -> {
            // Создаём join к коллекции userIds (таблица billing_users)
            ListJoin<Billing, Integer> userIdsJoin = root.joinList(Billing.Fields.userIds);
            // Условие: user_id = ?
            return cb.equal(userIdsJoin, userId);
        };
    }

    public static Specification<Billing> serviceIdIn(Collection<Integer> serviceIds) {
        return (root, query, cb) -> {
            if (serviceIds == null || serviceIds.isEmpty()) {
                return cb.conjunction(); // всегда true
            }
            return root.get(Billing.Fields.registeredService).get("id").in(serviceIds);
        };
    }

    public static Specification<Billing> stateIn(Collection<BillingStates> states) {
        return (root, query, cb) -> {
            if (states == null || states.isEmpty()) {
                return cb.conjunction(); // всегда true
            }
            return root.get(Billing.Fields.state).in(states);
        };
    }

    // Комбинируем
    public static Specification<Billing> withFilters(
            Integer userId,
            Collection<Integer> serviceIds,
            Collection<BillingStates> states
    ) {
        Specification<Billing> spec = Specification.unrestricted(); // ← начальная "нейтральная" спецификация

        if (userId != null) {
            spec = spec.and(hasUserId(userId));
        }

        if (serviceIds != null && !serviceIds.isEmpty()) {
            spec = spec.and(serviceIdIn(serviceIds));
        }

        if (states != null && !states.isEmpty()) {
            spec = spec.and(stateIn(states));
        }

        return spec;
    }
}
