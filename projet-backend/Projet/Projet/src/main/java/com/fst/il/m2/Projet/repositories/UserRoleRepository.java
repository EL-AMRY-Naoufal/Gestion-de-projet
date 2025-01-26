package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    Optional<List<UserRole>> findAllByRoleAndYearId(Role role, Long year);
    List<UserRole> findByUserIdAndYearId(Long id, Long currentYear);
    List <UserRole> findByRole(Role role);

    List<UserRole> findByRoleAndYearId(Role role, Long year);
}
