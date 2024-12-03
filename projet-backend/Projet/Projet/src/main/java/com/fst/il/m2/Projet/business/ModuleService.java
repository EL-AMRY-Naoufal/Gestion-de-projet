package com.fst.il.m2.Projet.business;

import java.util.List;

import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.models.Module;


public interface ModuleService {
    List<ModuleDto> getAllModules();
    Module getModuleById(Long id);
    Module addModule(Module module);
    void deleteModule(Long id);
}
