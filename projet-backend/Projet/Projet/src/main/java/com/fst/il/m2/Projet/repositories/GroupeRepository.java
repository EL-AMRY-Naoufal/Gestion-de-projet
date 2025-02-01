package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    @Query("SELECT g FROM Groupe g WHERE g.module = :module")
    List<Groupe> findGroupesByModule(Module module);
}
