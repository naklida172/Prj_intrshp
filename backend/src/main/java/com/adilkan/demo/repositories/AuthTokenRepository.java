package com.adilkan.demo.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adilkan.demo.entities.AuthToken;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long>{
    Optional<AuthToken> findByToken(UUID token);
}
