package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.models.Semestre;
import com.fst.il.m2.Projet.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceDefault implements ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private GroupeService groupeService;

    // Get all modules
    @Override
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }


    // Get a module by ID
    @Override
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElseThrow();
    }

    // Add a new module
    @Override
    public Module addModule(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public List<Module> getModulesBySemestre(Semestre semestre) {
        return moduleRepository.findModulesBySemestre(semestre);
    }

    @Override
    public Boolean hasGroupes(Long id) {
        return !groupeService.getGroupesByModule(getModuleById(id)).isEmpty();
    }
}
