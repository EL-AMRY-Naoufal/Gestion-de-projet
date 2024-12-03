package com.fst.il.m2.Projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.il.m2.Projet.models.Module;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
}
