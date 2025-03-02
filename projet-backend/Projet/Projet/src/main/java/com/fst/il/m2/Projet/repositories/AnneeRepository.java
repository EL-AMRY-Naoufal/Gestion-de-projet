package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Annee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface AnneeRepository extends JpaRepository<Annee, Long> {

    @Query("SELECT a FROM Annee a ORDER BY a.debut DESC LIMIT 1")
    Optional<Annee> getCurrentYear();

}
