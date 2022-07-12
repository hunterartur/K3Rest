package com.example.k3bootsecurity.repository;

import com.example.k3bootsecurity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.email=:email")
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
