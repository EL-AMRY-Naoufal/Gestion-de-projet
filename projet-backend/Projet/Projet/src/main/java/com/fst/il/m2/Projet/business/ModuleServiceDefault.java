package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.TypeHeure;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.repositories.ModuleRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

public class ModuleServiceDefault implements ModuleService{

    @Autowired
    private ModuleRepository moduleRepository;

    public ModuleServiceDefault(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void modifyAssignedHours(Long id, Map<TypeHeure, Integer> heuresParType) {
        var module = this.moduleRepository.findById(id).orElseThrow(() -> new RuntimeException("The module with id " + id + "does not exist"));
        module.setHeuresParType(heuresParType);
        moduleRepository.save(module);
    }
}
