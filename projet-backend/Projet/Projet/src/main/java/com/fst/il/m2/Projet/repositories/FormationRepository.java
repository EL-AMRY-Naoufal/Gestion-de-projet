package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Departement;
import com.fst.il.m2.Projet.models.Formation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
    @Query("SELECT f FROM Formation f WHERE f.departement = :departement")
    List<Formation> findDepartementFormations(Departement departement);
}
