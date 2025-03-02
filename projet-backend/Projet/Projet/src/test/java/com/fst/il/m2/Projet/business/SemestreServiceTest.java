package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.models.Semestre;
import com.fst.il.m2.Projet.repositories.SemestreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SemestreServiceTest {

    @Mock
    SemestreRepository semestreRepository;

    @InjectMocks
    SemestreServiceDefault semestreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSemestres_Success(){
        List<Semestre> semestres = List.of(Semestre.builder().id(1L).build(), Semestre.builder().id(2L).build());
        when(semestreRepository.findAll()).thenReturn(semestres);

        List<Semestre> results = semestreService.getAllSemestres();

        assertEquals(2, results.size());
        verify(semestreRepository, times(1)).findAll();
    }

    @Test
    void getSemestreById_Success(){
        Semestre semestre = Semestre.builder().id(1L).build();
        when(semestreRepository.findById(1L)).thenReturn(Optional.of(semestre));

        assertDoesNotThrow(() -> {
            Semestre result = semestreService.getSemestreById(1L);
            assertEquals(semestre, result);
            verify(semestreRepository, times(1)).findById(any());
        });
    }

    @Test
    void getSemestreById_SemestreDoesNotExist(){
        when(semestreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            semestreService.getSemestreById(1L);
        });

        verify(semestreRepository, times(1)).findById(any());
    }

    @Test
    void addSemestre_Success(){
        Semestre semestre = Semestre.builder().id(1L).build();
        when(semestreRepository.save(semestre)).thenReturn(semestre);

        assertDoesNotThrow(() -> {
            Semestre result = semestreService.addSemestre(semestre);
            assertNotNull(result);
        });

        verify(semestreRepository, times(1)).save(any());
    }

    @Test
    void deleteSemestre_Success(){
        doNothing().when(semestreRepository).deleteById(1L);

        assertDoesNotThrow(() -> {
            semestreService.deleteSemestre(1L);
        });

        verify(semestreRepository, times(1)).deleteById(any());
    }

    @Test
    void deleteSemestre_SemestreDoesNotExist(){
        doNothing().when(semestreRepository).deleteById(1L);

        assertDoesNotThrow(() -> {
            semestreService.deleteSemestre(1L);
        });

        verify(semestreRepository, times(1)).deleteById(any());
    }

}