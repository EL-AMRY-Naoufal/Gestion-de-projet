package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.models.Niveau;

import java.util.List;

public interface NiveauService {
    Niveau saveNiveau(Niveau niveau);
    Niveau getNiveauById(Long id);
    List<Niveau> getAllNiveaux();
    void deleteNiveau(Long id);
    List<Niveau> getNiveauxByFormation(Formation formation);
    Boolean hasSemestres(Long id);
}
