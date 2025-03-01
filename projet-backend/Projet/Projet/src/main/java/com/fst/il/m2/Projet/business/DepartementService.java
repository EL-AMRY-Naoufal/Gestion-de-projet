package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Departement;

import java.util.List;

public interface DepartementService {
    Departement saveDepartement(Departement departement);

    List<Departement> getAllDepartements();

    Departement getDepartementById(Long id);

    void deleteDepartement(Long id);

    List<Departement> getDepartementsByAnnee(Annee annee);

    Boolean hasFormations(Long id);
}
