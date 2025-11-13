package kos.progs.backend.repository;

import kos.progs.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface JPAPaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByUserIdAndCreatedAtAfterAndCreatedAtBefore(int userId, Instant after, Instant before);
}
