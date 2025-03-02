package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.repositories.GroupeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupeTest {

    @Mock
    private GroupeRepository groupeRepository;

    @InjectMocks
    private GroupeServiceDefault groupeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveGroupe_Success() {
        Groupe groupe = new Groupe();
        when(groupeRepository.save(any(Groupe.class))).thenReturn(groupe);

        Groupe result = groupeService.saveGroupe(groupe);

        assertNotNull(result);
        verify(groupeRepository, times(1)).save(groupe);
    }

    @Test
    void getGroupeById_ExistingId_ReturnsGroupe() {
        Groupe groupe = new Groupe();
        when(groupeRepository.findById(1L)).thenReturn(Optional.of(groupe));

        Groupe result = groupeService.getGroupeById(1L);

        assertNotNull(result);
        verify(groupeRepository, times(1)).findById(1L);
    }

    @Test
    void getGroupeById_NonExistingId_ThrowsException() {
        when(groupeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> groupeService.getGroupeById(1L));
        verify(groupeRepository, times(1)).findById(1L);
    }

    @Test
    void getGroupesByModule_ExistingModule_ReturnsListOfGroupes() {
        Module module = new Module();
        Groupe groupe = new Groupe();
        when(groupeRepository.findGroupesByModule(module)).thenReturn(Collections.singletonList(groupe));

        List<Groupe> result = groupeService.getGroupesByModule(module);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(groupeRepository, times(1)).findGroupesByModule(module);
    }

    @Test
    void getAllGroupes_ReturnsListOfGroupes() {
        Groupe groupe = new Groupe();
        when(groupeRepository.findAll()).thenReturn(Collections.singletonList(groupe));

        List<Groupe> result = groupeService.getAllGroupes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(groupeRepository, times(1)).findAll();
    }

    @Test
    void deleteGroupe_ExistingId_Success() {
        doNothing().when(groupeRepository).deleteById(1L);

        groupeService.deleteGroupe(1L);

        verify(groupeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteGroupe_NonExistingId_ThrowsException() {
        doThrow(new RuntimeException("Groupe not found")).when(groupeRepository).deleteById(1L);

        assertThrows(RuntimeException.class, () -> groupeService.deleteGroupe(1L));
        verify(groupeRepository, times(1)).deleteById(1L);
    }
}
