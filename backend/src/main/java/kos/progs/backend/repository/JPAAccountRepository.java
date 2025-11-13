package kos.progs.backend.repository;

import kos.progs.backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JPAAccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserId(int userId);
}
