package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
}
