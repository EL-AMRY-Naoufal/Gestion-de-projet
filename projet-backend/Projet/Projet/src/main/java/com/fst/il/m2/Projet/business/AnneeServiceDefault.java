package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.repositories.AnneeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnneeServiceDefault implements  AnneeService{

    @Autowired
    private AnneeRepository anneeRepository;

    @PostConstruct
    public void postContruct(){
        anneeRepository.save(Annee.builder().debut(1).id(1L).build());
        //anneeRepository.save(Annee.builder().debut(2).id(2L).build());
    }

    @Override
    public Annee saveAnnee(Annee annee) {
        return anneeRepository.save(annee);
    }

    @Override
    public List<Annee> getAllAnnees() {
        return anneeRepository.findAll();
    }

    @Override
    public Annee getAnneeById(Long id) {
        return anneeRepository.findById(id) .orElseThrow(() -> new RuntimeException("Anne not found"));
    }

    @Override
    public void deleteAnnee(Long id) {
        anneeRepository.deleteById(id);
    }

    @Override
    public Long getCurrentYearId(){
        return anneeRepository.getCurrentYear().orElseThrow(NotFoundException::new).getId();
    }


}
