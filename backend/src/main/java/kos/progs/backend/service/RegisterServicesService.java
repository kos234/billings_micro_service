package kos.progs.backend.service;

import kos.progs.backend.entity.RegisteredService;
import kos.progs.backend.repository.JPARegisteredServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterServicesService {
    private final JPARegisteredServiceRepository jpaRegisteredServiceRepository;

    public Optional<RegisteredService> getRegisteredServiceById(int id){
        return jpaRegisteredServiceRepository.findById(id);
    }

    public boolean hasRegisteredServiceById(int id){
        return jpaRegisteredServiceRepository.existsById(id);
    }

    public void registerService(RegisteredService registeredService){
        jpaRegisteredServiceRepository.save(registeredService);
    }
}
