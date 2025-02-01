package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    @Query("SELECT d FROM Departement d WHERE d.annee = :annee")
    List<Departement> findYearDepartements(Annee annee);
}
