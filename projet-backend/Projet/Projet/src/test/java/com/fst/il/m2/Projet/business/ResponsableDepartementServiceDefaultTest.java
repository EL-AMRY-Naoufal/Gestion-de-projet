package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResponsableDepartementServiceDefaultTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private ResponsableFormationRepository responsableFormationRepository;
    @Mock
    private ResponsableDepartementRepository responsableDepartementRepository;

    @Mock
    private EnseignantRepository enseignantRepository;
    @Mock
    private EnseignantService enseignantService;
    @InjectMocks
    private ResponsableDepartementServiceDefault responsableDepartementService;

    private User user;
    private User responsable;
    Annee annee;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");

        annee = new Annee();
        annee.setId(1L);
        annee.setDebut(2024);

        responsable = new User();
        responsable.setId(2L);
        responsable.addRole(annee,Role.CHEF_DE_DEPARTEMENT);
        responsable.setUsername("responsable");
        responsable.setFirstname("James");
        responsable.setName("Bond");
    }


    @Test
    void testCreateUser_ResponsableNotFound() {
        Long responsableId = 99L;
        Long currentYear = 2024L;
        boolean associateEnseignantWithUser = false;

        when(userRepository.findById(responsableId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                responsableDepartementService.createUser(user, responsableId, associateEnseignantWithUser, currentYear)
        );

        assertEquals("Responsable not found", exception.getMessage());
    }

    @Test
    void testCreateUser_UnauthorizedResponsable() {
        Long responsableId = 2L;
        Long currentYear = 2024L;
        boolean associateEnseignantWithUser = false;

        when(userRepository.findById(responsableId)).thenReturn(Optional.of(responsable));

        Exception exception = assertThrows(RuntimeException.class, () ->
                responsableDepartementService.createUser(user, responsableId, associateEnseignantWithUser, currentYear)
        );

        assertEquals("Only Responsable de Département can create users", exception.getMessage());
    }
    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> responsableDepartementService.getUserById(1L));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user, responsable));
        List<User> users = responsableDepartementService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(responsable));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        responsableDepartementService.deleteUser(1L, 2L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotAllowedForNonResponsable() {
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        Exception exception = assertThrows(RuntimeException.class, () -> responsableDepartementService.deleteUser(1L, 2L));
        assertEquals("Only Responsable de Département can delete users", exception.getMessage());
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = responsableDepartementService.getUserById(1L);

        assertNotNull(foundUser, "User should not be null");
        assertEquals(1L, foundUser.getId(), "User ID should match");
        assertEquals("testuser", foundUser.getUsername(), "User username should match");
    }

    @Test
    void testGetUserById_UserNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            responsableDepartementService.getUserById(1L);
        });

        assertEquals("User not found", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testGetUsersByRoleAndYear_UsersFound() {
        when(userRepository.searchUsersByRoles("", Role.CHEF_DE_DEPARTEMENT, 1L))
                .thenReturn(List.of(responsable));

        List<User> foundUsers = responsableDepartementService.getUsersByRoleAndYear("", Role.CHEF_DE_DEPARTEMENT, 1L);

        assertNotNull(foundUsers, "UserRoles list should not be null");
        assertEquals(1, foundUsers.size(), "The size of the list should be 1");
        assertEquals(Role.CHEF_DE_DEPARTEMENT, foundUsers.get(0).getRoles().get(0).getRole(), "The role should be CHEF_DE_DEPARTEMENT");
    }
}
