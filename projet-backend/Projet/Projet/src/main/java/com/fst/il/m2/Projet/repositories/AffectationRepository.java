package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AffectationRepository extends JpaRepository<Affectation, Long> {

    @Query("SELECT a FROM Affectation a WHERE a.id = :assignationId AND a.enseignant.id = :enseignantId")
    Optional<Affectation> findByEnseignantIdAndAssignationId(Long enseignantId, Long assignationId);

}
