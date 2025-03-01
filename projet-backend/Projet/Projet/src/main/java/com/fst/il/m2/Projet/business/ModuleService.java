package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.models.Niveau;
import com.fst.il.m2.Projet.models.Semestre;

import java.util.List;


public interface ModuleService {
    List<Module> getAllModules();
    Module getModuleById(Long id);
    Module addModule(Module module);
    void deleteModule(Long id);
    List<Module> getModulesBySemestre(Semestre semestre);
    Boolean hasGroupes(Long id);
}
