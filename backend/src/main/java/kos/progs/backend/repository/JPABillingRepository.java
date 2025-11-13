package kos.progs.backend.repository;

import kos.progs.backend.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface JPABillingRepository extends JpaRepository<Billing, UUID>, JpaSpecificationExecutor<Billing> {

}
