package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.ModuleService;
import com.fst.il.m2.Projet.dto.ModuleDto;
import com.fst.il.m2.Projet.mapper.ModuleMapper;
import com.fst.il.m2.Projet.models.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // Get all Modules
    @GetMapping
    public ResponseEntity<List<ModuleDto>> getAllModules() {
        List<Module> modules = moduleService.getAllModules();
        return new ResponseEntity<>(modules.stream().map(ModuleMapper::toDto).toList(), HttpStatus.OK);
    }

    // Get a Module by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        try {
            Module module = moduleService.getModuleById(id);
            return new ResponseEntity<>(module, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a new Module
    @PostMapping
    public ResponseEntity<ModuleDto> addModule(@RequestBody ModuleDto moduleDto) {
        Module savedModule = moduleService.addModule(ModuleMapper.toEntity(moduleDto));
        return new ResponseEntity<>(ModuleMapper.toDto(savedModule), HttpStatus.CREATED);
    }

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
