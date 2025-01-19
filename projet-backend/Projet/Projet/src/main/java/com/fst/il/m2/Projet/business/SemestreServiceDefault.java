package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Orientation;
import com.fst.il.m2.Projet.models.Semestre;
import com.fst.il.m2.Projet.repositories.SemestreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemestreServiceDefault implements SemestreService {
    @Autowired
    private SemestreRepository semestreRepository;

    @Override
    public List<Semestre> getAllSemestres() {
        return semestreRepository.findAll();
    }

    @Override
    public Semestre getSemestreById(Long id) {
        return semestreRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Semestre> getSemestresByOrientation(Orientation orientation) {
        return semestreRepository.findSemestresByOrientation(orientation);
    }

    @Override
    public Semestre addSemestre(Semestre semestre) {
        return semestreRepository.save(semestre);
    }

    @Override
    public void deleteSemestre(Long id) {
        semestreRepository.deleteById(id);
    }
}
