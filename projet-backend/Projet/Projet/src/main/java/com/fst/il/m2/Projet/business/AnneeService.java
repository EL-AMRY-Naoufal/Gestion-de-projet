package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Annee;

import java.util.List;
import java.util.Optional;

public interface AnneeService {
    Annee saveAnnee(Annee annee);

    List<Annee> getAllAnnees();

    Annee getAnneeById(Long id);

    void deleteAnnee(Long id);

}
