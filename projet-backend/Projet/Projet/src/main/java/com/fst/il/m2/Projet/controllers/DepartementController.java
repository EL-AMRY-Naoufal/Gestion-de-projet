package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.AnneeService;
import com.fst.il.m2.Projet.business.DepartementService;
import com.fst.il.m2.Projet.business.ResponsableDepartementService;
import com.fst.il.m2.Projet.dto.DepartementDto;
import com.fst.il.m2.Projet.mapper.DepartementMapper;
import com.fst.il.m2.Projet.models.Annee;
import com.fst.il.m2.Projet.models.Departement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
public class DepartementController {

    private final DepartementService departementService;
    private final ResponsableDepartementService responsableDepartementService;
    private final AnneeService anneeService;

    @PostMapping
    public ResponseEntity<DepartementDto> saveDepartement(@RequestBody DepartementDto departementDto) {
        DepartementMapper departementMapper = new DepartementMapper(responsableDepartementService);
        System.out.println("departement received : " + departementDto);
        Departement departementToSave = departementMapper.toEntity(departementDto);
        System.out.println(departementDto.getResponsableDepartementId());
        DepartementDto savedDepartementDto = departementMapper.toDto(departementService.saveDepartement(departementToSave));
        return new ResponseEntity<>(savedDepartementDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<DepartementDto> getDepartements() {
        List<Departement> departements = departementService.getAllDepartements();
        return departements.stream().map(new DepartementMapper(responsableDepartementService)::toDto).toList();
    }
    @GetMapping("/year/{anneeId}")
    public List<DepartementDto> getDepartementsByYear(@PathVariable Long anneeId) {
        List<Departement> departements = departementService.getDepartementsByAnnee(anneeService.getAnneeById(anneeId));
        return departements.stream().map(new DepartementMapper(responsableDepartementService)::toDto).toList();
    }

    @GetMapping("/id/{id}")
    public Departement getDepartementById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }


}
