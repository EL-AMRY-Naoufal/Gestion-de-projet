package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.ResponsableDepartement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsableDepartementRepository extends JpaRepository<ResponsableDepartement, Long> {
}
