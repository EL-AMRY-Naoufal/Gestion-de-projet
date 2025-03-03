package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.GroupeDto;
import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fst.il.m2.Projet.repositories.ModuleRepository;
import com.fst.il.m2.Projet.models.Module;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleServiceDefault implements ModuleService{
    @Autowired
    private ModuleRepository moduleRepository;

    // Get all modules
    @Override
    public List<ModuleDto> getAllModules() {
        List<Module> modules = moduleRepository.findAll();
        return modules.stream().map(
                module -> new ModuleDto(
                        module.getId(),
                        module.getNom(),
                        module.getGroupes().stream().map(
                                groupe -> new GroupeDto(
                                        groupe.getId(),
                                        groupe.getNom()
                                )
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }


    // Get a module by ID
    @Override
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElseThrow(NotFoundException::new);
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
