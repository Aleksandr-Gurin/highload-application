package com.example.userservice.repository;

import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class UserDatabaseClient {
    private final DatabaseClient client;
    private final UserMapper mapper;

    public Mono<User> findByUsername(String username) {
        String sql = String.format("SELECT u.id, username, password, role_id, name FROM users u JOIN roles r " +
                "ON r.id = u.role_id WHERE username='%s'", username);
        return client.sql(sql).map(mapper::rowToUser).one();
    }

    public Mono<User> findById(UUID id) {
        String sql = "SELECT u.id, username, password, role_id, name FROM users u JOIN roles r ON r.id = u.role_id " +
                "WHERE u.id='" + id + "'";
        return client.sql(sql).map(mapper::rowToUser).one();
    }

    public Flux<User> findAll(Pageable pageable) {
        String sql = String.format("SELECT u.id, username, password, role_id, name FROM users u JOIN roles r " +
                "ON r.id = u.role_id LIMIT %d OFFSET %d", pageable.getPageSize(), pageable.getOffset());
        return client.sql(sql).map(mapper::rowToUser).all();
    }

    public Flux<User> findAllByRole(String role, Pageable pageable) {
        String sql = String.format("SELECT u.id, username, password, role_id, name FROM users u JOIN roles r " +
                        "ON r.id = u.role_id WHERE r.name='%s' LIMIT %d OFFSET %d",
                role, pageable.getPageSize(), pageable.getOffset());
        return client.sql(sql).map(mapper::rowToUser).all();
    }
}
