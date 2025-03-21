package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.ResponsableDepartement;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.models.UserRole;

import java.util.List;

public interface ResponsableDepartementService {
    User createUser(User user, Long responsableId, boolean associateEnseignantWithUser, Long currentYear); // `responsableId` will be used to check the role.
    User getUserById(Long id);
    ResponsableDepartement getResponsableDepartementById(Long id);
    List<User> getAllUsers();
    List<User> getUsersByUsername(String username);
    List<UserRole> getRolesByUserIdAndYear(Long userId, Long year);
    User updateUser(Long id, User user, Long responsableId, Long currentYear);
    void deleteUser(Long id, Long responsableId);
     List<UserRole> getUsersByRole(Role role);
     List<User> getUsersByRoleAndYear(String q, Role role, Long year);
}
