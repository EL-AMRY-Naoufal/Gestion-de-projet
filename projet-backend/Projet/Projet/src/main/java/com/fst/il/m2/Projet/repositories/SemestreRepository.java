package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Niveau;
import com.fst.il.m2.Projet.models.Semestre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    @Query("SELECT s FROM Semestre s WHERE s.niveau = :niveau")
    List<Semestre> findSemestresByNiveau(Niveau niveau);
}
