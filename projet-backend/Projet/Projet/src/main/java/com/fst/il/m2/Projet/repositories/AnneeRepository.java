package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Annee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnneeRepository extends JpaRepository<Annee, Long> {
}
