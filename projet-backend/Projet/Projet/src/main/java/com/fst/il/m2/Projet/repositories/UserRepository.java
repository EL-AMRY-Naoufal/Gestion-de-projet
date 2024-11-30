package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = %:email%")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
    List<User> findUserByRoles(Role role);
}
