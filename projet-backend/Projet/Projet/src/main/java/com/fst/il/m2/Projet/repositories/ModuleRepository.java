package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.models.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query("SELECT m FROM Module m WHERE m.semestre = :semestre")
    List<Module> findModulesBySemestre(Semestre semestre);
}
