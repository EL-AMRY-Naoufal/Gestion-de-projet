package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.User;

import java.util.List;
import java.util.Optional;

public interface ResponsableDepartementService {
    User createUser(User user, Long responsableId, boolean associateEnseignantWithUser, Long currentYear); // `responsableId` will be used to check the role.
    User getUserById(Long id);
    List<User> getAllUsers();

    List<User> getUsersByUsername(String username);
    List<User> getUsersByRole(Role role);
    User updateUser(Long id, User user, Long responsableId, Long currentYear);
    void deleteUser(Long id, Long responsableId);
    Affectation affecterModuleToEnseignant(Long enseignantId, Long moduleId, int heuresAssignees);
}
