package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnseignantServiceTest {

    @Mock
    private EnseignantRepository enseignantRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EnseignantService enseignantService;

    private static final Long currentYear = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEnseignant_Success() {
        // Mock User
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.addRole(currentYear, Role.ENSEIGNANT);

        // Mock Repository Behavior
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(enseignantRepository.save(any(Enseignant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Enseignant result = enseignantService.createEnseignant(
                1L,
                20,
                5,
                CategorieEnseignant.PROFESSEUR,
                10,
                1L
        );

        // Assert
        assertNotNull(result);
        assertEquals(20, result.getMaxHeuresService());
        assertEquals(5, result.getHeuresAssignees());
        assertTrue(result.getCategorieEnseignant().containsKey(CategorieEnseignant.PROFESSEUR));
        assertEquals(10, result.getCategorieEnseignant().get(CategorieEnseignant.PROFESSEUR));

        // Verify Repository Calls
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        verify(enseignantRepository, times(1)).save(any(Enseignant.class));
    }

    @Test
    void testCreateEnseignant_UserNotFound() {
        // Mock Repository Behavior
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                enseignantService.createEnseignant(1L, 20, 5, CategorieEnseignant.PROFESSEUR, 10, 1L));

        assertEquals("User not found with id: 1", exception.getMessage());

        // Verify Repository Calls
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
        verify(enseignantRepository, never()).save(any(Enseignant.class));
    }

    @Test
    void testUpdateEnseignant_Success() {
        // Mock Enseignant
        Enseignant mockEnseignant = new Enseignant();
        mockEnseignant.setId(1L);
        mockEnseignant.setMaxHeuresService(10);
        mockEnseignant.setCategorieEnseignant(new HashMap<>());

        // Mock Repository Behavior
        when(enseignantRepository.getReferenceById(1L)).thenReturn(mockEnseignant);
        when(enseignantRepository.save(any(Enseignant.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Enseignant result = enseignantService.updateEnseignant(
                1L,
                30,
                CategorieEnseignant.DOCTORANT,
                15
        );

        // Assert
        assertNotNull(result);
        assertEquals(30, result.getMaxHeuresService());
        assertTrue(result.getCategorieEnseignant().containsKey(CategorieEnseignant.DOCTORANT));
        assertEquals(15, result.getCategorieEnseignant().get(CategorieEnseignant.DOCTORANT));

        // Verify Repository Calls
        verify(enseignantRepository, times(1)).getReferenceById(1L);
        verify(enseignantRepository, times(1)).save(any(Enseignant.class));
    }

    @Test
    void testUpdateEnseignant_EnseignantNotFound() {
        // Mock Repository Behavior
        when(enseignantRepository.getReferenceById(1L)).thenThrow(new RuntimeException("Entity not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                enseignantService.updateEnseignant(1L, 30, CategorieEnseignant.PROFESSEUR, 15));

        assertEquals("Entity not found", exception.getMessage());

        // Verify Repository Calls
        verify(enseignantRepository, times(1)).getReferenceById(1L);
        verify(enseignantRepository, never()).save(any(Enseignant.class));
    }
}
