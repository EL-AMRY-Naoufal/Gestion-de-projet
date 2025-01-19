package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;

import java.util.List;

public interface GroupeService {
    Groupe saveGroupe(Groupe groupe); // Method to save a new group
    Groupe getGroupeById(Long id); // Method to fetch a group by its ID
    List<Groupe> getAllGroupes(); // Method to get all groups
    void deleteGroupe(Long id); // Method to delete a group by its ID
    List<Groupe> getGroupesByModule(Module module);
}
