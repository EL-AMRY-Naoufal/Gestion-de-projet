package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, User> {
}
