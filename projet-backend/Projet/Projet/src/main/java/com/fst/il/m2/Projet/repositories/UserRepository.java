package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    Optional<User> findUserByEmail(String email);
}
