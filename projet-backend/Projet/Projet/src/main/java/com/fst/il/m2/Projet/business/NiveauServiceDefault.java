package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Niveau;
import com.fst.il.m2.Projet.repositories.NiveauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NiveauServiceDefault implements NiveauService {

    @Autowired
    private NiveauRepository niveauRepository;

    @Override
    public Niveau saveNiveau(Niveau niveau) {
        return niveauRepository.save(niveau);
    }

    @Override
    public Niveau getNiveauById(Long id) {
        return niveauRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Niveau> getAllNiveaux() {
        return niveauRepository.findAll();
    }

    @Override
    public void deleteNiveau(Long id) {
        niveauRepository.deleteById(id);
    }
}
