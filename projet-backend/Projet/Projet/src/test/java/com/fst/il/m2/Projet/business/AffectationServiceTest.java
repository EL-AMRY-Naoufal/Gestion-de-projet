package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.exceptions.NotFoundException;
import com.fst.il.m2.Projet.models.Affectation;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.Groupe;
import com.fst.il.m2.Projet.repositories.AffectationRepository;
import com.fst.il.m2.Projet.repositories.EnseignantRepository;
import com.fst.il.m2.Projet.repositories.GroupeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AffectationServiceTest {

    @Mock
    private AffectationRepository affectationRepository;

    @Mock
    private EnseignantRepository enseignantRepository;

    @Mock
    private GroupeRepository groupeRepository;

    @Mock
    private Enseignant enseignantMock;

    @Mock
    private Groupe groupeMock;

    @InjectMocks
    private AffectationServiceDefault affectationService;

    private static Affectation affectation;

    private static final LocalDate FIXED_DATE = LocalDate.of(2024, 3, 15);

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);

        affectation = Affectation.builder()
                .enseignant(enseignantMock)
                .groupe(groupeMock)
                .heuresAssignees(20)
                .dateAffectation(FIXED_DATE)
                .commentaire("")
                .build();
    }

    @Test
    void testAffecterModuleToEnseignant_Right() {
        try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
            mockedDate.when(LocalDate::now).thenReturn(FIXED_DATE);

            when(enseignantRepository.findById(1L)).thenReturn(java.util.Optional.of(enseignantMock));
            when(groupeRepository.findById(1L)).thenReturn(java.util.Optional.of(groupeMock));
            when(affectationRepository.existsByEnseignantAndGroupe(enseignantMock, groupeMock)).thenReturn(false);
            when(affectationRepository.save(any(Affectation.class))).thenReturn(affectation);
            //when((enseignantMock.getHeuresAssignees())).thenReturn(20);
            when((groupeMock.getHeuresAffectees())).thenReturn(20);

            Affectation result = affectationService.affecterGroupeToEnseignant(1L, 1L, 20,1L);

            verify(groupeRepository, times(1)).save(groupeMock);
            verify(groupeMock, times(1)).setHeuresAffectees(Mockito.intThat(i -> i == 40));
            verify(affectationRepository, times(1)).save(any(Affectation.class));
            //verify(enseignantMock, times(1)).setHeuresAssignees(Mockito.intThat(i -> i == 40));
            assertEquals(affectation, result);
        }
    }

    @Test
    void testAffecterModuleToEnseignant_EnseignantDoesntExist() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
                mockedDate.when(LocalDate::now).thenReturn(FIXED_DATE);

                when(enseignantRepository.findById(1L)).thenReturn(java.util.Optional.empty());

                affectationService.affecterGroupeToEnseignant(1L, 1L, 20, 1L);
            }
        });
    }

    @Test
    void testAffecterModuleToEnseignant_GroupDoesntExist() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
                mockedDate.when(LocalDate::now).thenReturn(FIXED_DATE);

                when(enseignantRepository.findById(1L)).thenReturn(java.util.Optional.of(enseignantMock));
                when(groupeRepository.findById(1L)).thenReturn(java.util.Optional.empty());

                affectationService.affecterGroupeToEnseignant(1L, 1L, 20, 1L);
            }
        });
    }

    @Test
    void testAffecterModuleToEnseignant_AffectationAlreadyExists() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            try (MockedStatic<LocalDate> mockedDate = Mockito.mockStatic(LocalDate.class)) {
                mockedDate.when(LocalDate::now).thenReturn(FIXED_DATE);
                when(enseignantRepository.findById(1L)).thenReturn(java.util.Optional.of(enseignantMock));
                when(groupeRepository.findById(1L)).thenReturn(java.util.Optional.of(groupeMock));
                when(affectationRepository.existsByEnseignantAndGroupe(enseignantMock, groupeMock)).thenReturn(true);

                affectationService.affecterGroupeToEnseignant(1L, 1L, 20,1L);
            }
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void testAffecterModuleToEnseignant_NegativeOrZeroHoursGave(int heuresAssignees) {
        assertThrows(RuntimeException.class, () -> {
            affectationService.affecterGroupeToEnseignant(1L, 1L, heuresAssignees, 1L);
        });
    }

    @Test
    void testUpdateAffectationHours_Right() {
        when(affectationRepository.findById(1L)).thenReturn(java.util.Optional.of(affectation));

        affectationService.updateAffectationHours(1L, 30, 1L);

        verify(affectationRepository, times(1)).findById(1L);
        verify(affectationRepository, times(1)).save(affectation);
        assertEquals(30, affectation.getHeuresAssignees());
    }

    @Test
    void testUpdateAffectationHours_AffectationDoesntExist() {
        when(affectationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            affectationService.updateAffectationHours(1L, 30,1L);
        });

        verify(affectationRepository, times(1)).findById(1L);
        verify(affectationRepository, never()).save(affectation);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void testUpdateAffectationHours_NegativeOrZeroHoursGave(int hours) {
        assertThrows(RuntimeException.class, () -> {
            affectationService.updateAffectationHours(1L, hours,1L);
        });

        verify(affectationRepository, never()).findById(any());
        verify(affectationRepository, never()).save(affectation);
    }

    @Test
    void testDeleteAffectation_Right() {
        when(affectationRepository.findById(1L)).thenReturn(java.util.Optional.of(affectation));
        //when(enseignantMock.getHeuresAssignees()).thenReturn(20);
        when(groupeMock.getHeuresAffectees()).thenReturn(20);

        affectationService.deleteAffectation(1L,1L);

        //verify(enseignantMock, times(1)).setHeuresAssignees(Mockito.intThat(i -> i == 0));
        verify(groupeMock, times(1)).setHeuresAffectees(Mockito.intThat(i -> i == 0));
        verify(affectationRepository, times(1)).findById(1L);
        verify(affectationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteAffectation_AffectationDoesntExist() {
        when(affectationRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            affectationService.deleteAffectation(1L,1L);
        });

        verify(affectationRepository, times(1)).findById(1L);
        verify(affectationRepository, never()).deleteById(1L);
    }

}