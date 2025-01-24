package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.UserRole;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<List<UserRole>> findAllByRoleAndYear(Role role, Long year);
    List<UserRole> findByUserIdAndYear(Long id, Long currentYear);
    List <UserRole> findByRole(Role role);
    List<UserRole> findByRoleAndYear(Role role, Long year);
}
