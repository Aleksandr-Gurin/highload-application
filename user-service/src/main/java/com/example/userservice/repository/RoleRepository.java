package com.example.userservice.repository;

import com.example.userservice.model.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, UUID> {
    Mono<Role> findByName(String name);

    Mono<Boolean> existsByName(String name);

    Flux<Role> findAllBy(Pageable pageable);
}
