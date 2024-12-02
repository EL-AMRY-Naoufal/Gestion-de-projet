package com.fst.il.m2.Projet.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fst.il.m2.Projet.repositories.ModuleRepository;
import com.fst.il.m2.Projet.models.Module;


import java.util.List;

@Service
public class ModuleServiceDefault implements ModuleService{
    @Autowired
    private ModuleRepository moduleRepository;

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
}
