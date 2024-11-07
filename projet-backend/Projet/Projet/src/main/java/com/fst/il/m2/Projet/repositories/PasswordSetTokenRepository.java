package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.PasswordSetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordSetTokenRepository extends JpaRepository<PasswordSetToken, Long> {
    Optional<PasswordSetToken> findByToken(String token);
}