package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.ModuleService;
import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.mapper.ModuleMapper;
import com.fst.il.m2.Projet.models.Module;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Module Controller", description = "Gestion des modules")
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @Operation(summary = "Récupérer tous les modules", description = "Cette méthode permet de récupérer la liste de tous les modules.")
    @ApiResponse(responseCode = "200", description = "Liste des modules récupérée")
    // Get all Modules
    @GetMapping
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        List<Module> modules = moduleService.getAllModules();
        return new ResponseEntity<>(modules.stream().map(ModuleMapper::toDto).toList(), HttpStatus.OK);
    }

    @Operation(summary = "Récupérer un module par ID", description = "Cette méthode permet de récupérer un module par son ID.")
    @ApiResponse(responseCode = "200", description = "Module récupéré avec succès")
    @ApiResponse(responseCode = "404", description = "Module non trouvé")
    // Get a Module by ID
    @GetMapping("/{id}")
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable Long id) {
        try {
            Module module = moduleService.getModuleById(id);
            ModuleDto moduleDto = ModuleMapper.toDto(module);
            return new ResponseEntity<>(moduleDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Ajouter un nouveau module", description = "Cette méthode permet d'ajouter un nouveau module.")
    @ApiResponse(responseCode = "201", description = "Module ajouté avec succès")
    // Add a new Module
    @PostMapping
    public ResponseEntity<ModuleDto> addModule(@RequestBody ModuleDto moduleDto) {
        Module savedModule = moduleService.addModule(ModuleMapper.toEntity(moduleDto));
        return new ResponseEntity<>(ModuleMapper.toDto(savedModule), HttpStatus.CREATED);
    }

    @Operation(summary = "Supprimer un module par ID", description = "Cette méthode permet de supprimer un module par son ID.")
    @ApiResponse(responseCode = "204", description = "Module supprimé avec succès")
    @ApiResponse(responseCode = "406", description = "Module ne peut pas être supprimé")
    // Delete a Module by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        if(!moduleService.hasGroupes(id)) {
            moduleService.deleteModule(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
