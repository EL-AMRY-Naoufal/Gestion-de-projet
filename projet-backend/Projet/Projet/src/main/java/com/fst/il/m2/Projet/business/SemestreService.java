package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Niveau;
import com.fst.il.m2.Projet.models.Semestre;

import java.util.List;

public interface SemestreService {
    List<Semestre> getAllSemestres();
    Semestre getSemestreById(Long id);
    Semestre addSemestre(Semestre semestre);
    void deleteSemestre(Long id);
    List<Semestre> getSemestresByNiveau(Niveau niveau);
}
