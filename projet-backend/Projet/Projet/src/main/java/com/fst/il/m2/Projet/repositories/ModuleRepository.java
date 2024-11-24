package com.fst.il.m2.Projet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fst.il.m2.Projet.models.Module;


public interface ModuleRepository extends JpaRepository<Module, Long> {
}
