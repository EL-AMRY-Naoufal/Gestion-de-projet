package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

    //findEnseignantIdByUserId
    @Query("SELECT e FROM Enseignant e WHERE e.user.id = :userId")
    Optional<Enseignant> findByUserId(Long userId);
}
