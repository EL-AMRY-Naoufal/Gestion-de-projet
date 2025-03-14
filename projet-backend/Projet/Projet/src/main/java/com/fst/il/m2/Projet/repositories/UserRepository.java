package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.email LIKE %:email%")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> findUsersByUsername(String username);

    @Query("SELECT u, ur.role FROM User u JOIN UserRole ur WHERE ur.role = :role")
    List<User> findUserByRoles(Role role);

    @Query("SELECT u, ur.role FROM User u JOIN UserRole ur WHERE ur.role != :role")
    List<User> findUsersByRolesNotLike(Role role);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findOneUserByUsername(String username);
    List<User> findUsersByNameAndFirstname(String name, String firstname);

    @Query("SELECT u FROM User u JOIN UserRole ur ON u.id = ur.user.id WHERE (LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))" +
            "AND (ur.role = :role AND ur.year.id = :yearId)")
    List<User> searchUsersByRoles(@Param("searchTerm") String searchTerm, @Param("role") Role role, @Param("yearId") Long yearId);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> searchUsers(@Param("searchTerm") String searchTerm);
}
