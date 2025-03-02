package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.repositories.GroupeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupeServiceDefault implements GroupeService {

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private AffectationService affectationService;

    @Override
    public Groupe saveGroupe(Groupe groupe) {
        return groupeRepository.save(groupe); // Save and return the saved group
    }

    @Override
    public Groupe getGroupeById(Long id) {
        return groupeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Groupe> getGroupesByModule(Module module) {
        return groupeRepository.findGroupesByModule(module);
    }

    @Override
    public Boolean hasAffectations(Long id) {
        return !affectationService.getAffectationsByGroupe(getGroupeById(id)).isEmpty();
    }

    @Override
    public List<Groupe> getAllGroupes() {
        return groupeRepository.findAll(); // Get all groups
    }

    @Override
    public void deleteGroupe(Long id) {
        groupeRepository.deleteById(id); // Delete a group by its ID
    }
}
