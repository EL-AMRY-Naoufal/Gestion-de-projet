package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.models.Module;
import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.repositories.ModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleServiceDefault moduleService;

    private Module module1;
    private Module module2;
    private Groupe groupe1;
    private Groupe groupe2;

    @BeforeEach
    void init() {
        groupe1 = Groupe.builder()
                .id(1L)
                .nom("Groupe 1")
                .build();

        groupe2 = Groupe.builder()
                .id(2L)
                .nom("Groupe 2")
                .build();

        module1 = Module.builder()
                .id(1L)
                .nom("Module 1")
                .groupes(Collections.singletonList(groupe1))
                .build();

        module2 = Module.builder()
                .id(2L)
                .nom("Module 2")
                .groupes(Collections.singletonList(groupe2))
                .build();
    }

    @Test
    void testGetAllModules() {
        when(moduleRepository.findAll()).thenReturn(Arrays.asList(module1, module2));

        List<Module> result = moduleService.getAllModules();

        assertEquals(2, result.size());
        assertEquals("Module 1", result.get(0).getNom());
        assertEquals("Module 2", result.get(1).getNom());
        assertEquals(1, result.get(0).getGroupes().size());
        assertEquals("Groupe 1", result.get(0).getGroupes().get(0).getNom());

        verify(moduleRepository, times(1)).findAll();
    }

    @Test
    void testGetModuleById() {
        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module1));

        Module result = moduleService.getModuleById(1L);

        assertEquals(module1.getId(), result.getId());
        assertEquals(module1.getNom(), result.getNom());
        verify(moduleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetModuleById_NotFound() {
        when(moduleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            moduleService.getModuleById(99L);
        });

        verify(moduleRepository, times(1)).findById(99L);
    }

    @Test
    void testAddModule() {
        when(moduleRepository.save(module1)).thenReturn(module1);

        Module result = moduleService.addModule(module1);

        assertEquals(module1.getId(), result.getId());
        assertEquals(module1.getNom(), result.getNom());
        verify(moduleRepository, times(1)).save(module1);
    }

    @Test
    void testDeleteModule() {
        doNothing().when(moduleRepository).deleteById(1L);

        moduleService.deleteModule(1L);

        verify(moduleRepository, times(1)).deleteById(1L);
    }
}