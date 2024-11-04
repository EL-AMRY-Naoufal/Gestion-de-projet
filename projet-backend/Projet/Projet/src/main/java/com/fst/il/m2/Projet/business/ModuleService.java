package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.TypeHeure;

import java.util.Map;

public interface ModuleService {
    void modifyAssignedHours(Long id, Map<TypeHeure, Integer> heuresParType);
}
