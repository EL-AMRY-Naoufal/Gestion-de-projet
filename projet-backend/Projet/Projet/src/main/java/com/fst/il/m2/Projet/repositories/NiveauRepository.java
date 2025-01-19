package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {

    @Query("SELECT n FROM Niveau n WHERE n.formation = :formation")
    List<Niveau> findNiveauxByFormation(Formation formation);
}
