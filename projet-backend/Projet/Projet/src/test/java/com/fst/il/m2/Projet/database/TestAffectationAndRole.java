package com.fst.il.m2.Projet.database;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.User;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

public class TestAffectationAndRole {

    static final Long currentYearId = 1L;

    // Tests pour l'entité User
    @Test
    public void testCreateUserWithMultipleRoles() {
        User user = new User("jdoe", "password123", "jdoe@example.com", Map.of(currentYearId, Role.ENSEIGNANT, currentYearId, Role.CHEF_DE_DEPARTEMENT));

        assertEquals(2, user.getRoles().size());
        assertTrue(user.hasRoleForYear(currentYearId, Role.CHEF_DE_DEPARTEMENT));
        assertTrue(user.hasRoleForYear(currentYearId, Role.ENSEIGNANT));
    }

    @Test
    public void testUserRoleAssignment() {
        User user = new User("jdoe", "password123", "jdoe@example.com", Map.of(1L, Role.ENSEIGNANT, 1L, Role.SECRETARIAT_PEDAGOGIQUE));

        assertTrue(user.hasRoleForYear(currentYearId, Role.SECRETARIAT_PEDAGOGIQUE));
        assertFalse(user.hasRoleForYear(currentYearId, Role.CHEF_DE_DEPARTEMENT));
    }

 /*   @Test
    public void testCreateEnseignantWithAffectations() {
        User user = new User("jdoe", "password123", "jdoe@example.com", List.of(Role.ENSEIGNANT));
        Affectation affectation1 = new Affectation(null, 10, LocalDate.now(), null, null);
        Affectation affectation2 = new Affectation(null, 5, LocalDate.now(), null, null);

        Enseignant enseignant = new Enseignant(null, CategorieEnseignant.PROFESSEUR, 20, 15, List.of(affectation1, affectation2), user);

        assertEquals(2, enseignant.getAffectations().size());
        assertEquals(user, enseignant.getUser());
    }*/

    @Test
    public void testAffectationDate() {
        Affectation affectation = new Affectation();
        affectation.setDateAffectation(LocalDate.now().plusDays(1));

        assertTrue("La date d'affectation doit être aujourd'hui ou dans le futur.", !affectation.getDateAffectation().isBefore(LocalDate.now()));
    }



    @Test
    void testAddAndRemoveRole() {
        User user = new User("janedoe", "password456", "janedoe@example.com", Map.of(currentYearId, Role.ENSEIGNANT));

        // Ajouter un rôle
        user.setRoles(Map.of(currentYearId, Role.CHEF_DE_DEPARTEMENT));
        assertTrue(user.hasRoleForYear(currentYearId, Role.CHEF_DE_DEPARTEMENT));

        // Supprimer un rôle
        user.getRoles().remove(Role.ENSEIGNANT);
        assertFalse(user.hasRoleForYear(currentYearId, Role.ENSEIGNANT));
    }
}
