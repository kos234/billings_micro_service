package kos.progs.backend.repository;

import kos.progs.backend.entity.RegisteredService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPARegisteredServiceRepository extends JpaRepository<RegisteredService, Integer> {

}
