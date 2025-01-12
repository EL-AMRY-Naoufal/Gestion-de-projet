package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Departement;
import com.fst.il.m2.Projet.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementServiceDefault implements DepartementService {
    @Autowired
    DepartementRepository departementRepository;

    @Override
    public Departement saveDepartement(Departement departement) {
        return departementRepository.save(departement);
    }

    @Override
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public Departement getDepartementById(Long id) {
        return departementRepository.findById(id).orElseThrow(() -> new RuntimeException("departement not found"));
    }

    @Override
    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }
}
