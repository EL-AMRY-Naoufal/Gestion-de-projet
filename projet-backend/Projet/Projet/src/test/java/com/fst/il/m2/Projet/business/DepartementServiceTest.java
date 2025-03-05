package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Departement;
import com.fst.il.m2.Projet.models.Formation;
import com.fst.il.m2.Projet.repositories.DepartementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartementServiceTest {

    @Mock
    private DepartementRepository departementRepository;

    @Mock FormationServiceDefault formationService;

    @InjectMocks
    private DepartementServiceDefault departementService;

    private Departement departement;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);

        departement = Departement.builder()
                .id(1L)
                .annee(Annee.builder().id(1L).debut(2024).build())
                .nom("departement")
                .build();
    }

    @Test
    void testSaveDerpartement_Right() {
        when(departementRepository.save(departement)).thenReturn(departement);

        Departement savedDepartement = departementService.saveDepartement(departement);

        Assertions.assertEquals(departement, savedDepartement);
        verify(departementRepository, times(1)).save(departement);
    }

    @Test
    void testGetAllDepartements_Right() {
        when(departementRepository.findAll()).thenReturn(java.util.List.of(departement));

        Assertions.assertEquals(java.util.List.of(departement), departementService.getAllDepartements());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDepartements_NoDepartement() {
        when(departementRepository.findAll()).thenReturn(java.util.List.of());

        Assertions.assertEquals(java.util.List.of(), departementService.getAllDepartements());
        verify(departementRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartementById_Right() {
        when(departementRepository.findById(1L)).thenReturn(java.util.Optional.of(departement));

        Assertions.assertEquals(departement, departementService.getDepartementById(1L));
        verify(departementRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDepartementById_NotFound() {
        when(departementRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> departementService.getDepartementById(1L));
        verify(departementRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteDepartement_Right() {
        departementService.deleteDepartement(1L);

        verify(departementRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetDepartementsByAnnee_Right() {
        when(departementRepository.findYearDepartements(departement.getAnnee())).thenReturn(java.util.List.of(departement));

        Assertions.assertEquals(java.util.List.of(departement), departementService.getDepartementsByAnnee(departement.getAnnee()));
        verify(departementRepository, times(1)).findYearDepartements(departement.getAnnee());
    }

    @Test
    void testHasFormations_True() {
        when(departementRepository.findById(1L)).thenReturn(java.util.Optional.of(departement));
        when(formationService.getFormationsByDepartement(departement)).thenReturn(java.util.List.of(Formation.builder().build()));

        Assertions.assertTrue(departementService.hasFormations(1L));
        verify(departementRepository, times(1)).findById(1L);
    }

}