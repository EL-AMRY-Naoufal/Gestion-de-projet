package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.repositories.FormationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FormationTest {

    @Mock
    private FormationRepository formationRepository;

    @InjectMocks
    private FormationServiceDefault formationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFormations_ReturnsListOfFormations() {
        Formation formation = new Formation();
        when(formationRepository.findAll()).thenReturn(Collections.singletonList(formation));

        List<Formation> result = formationService.getAllFormations();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(formationRepository, times(1)).findAll();
    }

    @Test
    void getFormationById_ExistingId_ReturnsFormation() {
        Formation formation = new Formation();
        when(formationRepository.findById(1L)).thenReturn(Optional.of(formation));

        Formation result = formationService.getFormationById(1L);

        assertNotNull(result);
        verify(formationRepository, times(1)).findById(1L);
    }

    @Test
    void getFormationById_NonExistingId_ReturnsNull() {
        when(formationRepository.findById(1L)).thenReturn(Optional.empty());

        Formation result = formationService.getFormationById(1L);

        assertNull(result);
        verify(formationRepository, times(1)).findById(1L);
    }

    @Test
    void saveFormation_Success() {
        Formation formation = new Formation();
        when(formationRepository.save(any(Formation.class))).thenReturn(formation);

        Formation result = formationService.saveFormation(formation);

        assertNotNull(result);
        verify(formationRepository, times(1)).save(formation);
    }

    @Test
    void updateFormation_ExistingId_Success() {
        Formation existingFormation = new Formation();
        existingFormation.setId(1L);
        when(formationRepository.findById(1L)).thenReturn(Optional.of(existingFormation));
        when(formationRepository.save(any(Formation.class))).thenReturn(existingFormation);

        Formation updatedFormation = new Formation();
        updatedFormation.setNom("Updated Name");
        Formation result = formationService.updateFormation(1L, updatedFormation);

        assertNotNull(result);
        assertEquals("Updated Name", result.getNom());
        verify(formationRepository, times(1)).findById(1L);
        verify(formationRepository, times(1)).save(existingFormation);
    }

    @Test
    void updateFormation_NonExistingId_ThrowsException() {
        when(formationRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                formationService.updateFormation(1L, new Formation()));

        assertEquals("Formation not found with id 1", exception.getMessage());
        verify(formationRepository, times(1)).findById(1L);
        verify(formationRepository, never()).save(any(Formation.class));
    }

    @Test
    void deleteFormation_ExistingId_Success() {
        when(formationRepository.existsById(1L)).thenReturn(true);

        formationService.deleteFormation(1L);

        verify(formationRepository, times(1)).existsById(1L);
        verify(formationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteFormation_NonExistingId_ThrowsException() {
        when(formationRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                formationService.deleteFormation(1L));

        assertEquals("Formation not found with id 1", exception.getMessage());
        verify(formationRepository, times(1)).existsById(1L);
        verify(formationRepository, never()).deleteById(1L);
    }

}
