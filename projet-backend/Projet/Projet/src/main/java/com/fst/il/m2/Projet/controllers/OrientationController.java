package com.fst.il.m2.Projet.controllers;

import com.fst.il.m2.Projet.business.OrientationService;
import com.fst.il.m2.Projet.models.Orientation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orientations")
public class OrientationController {

    private final OrientationService orientationService;

    @Autowired
    public OrientationController(OrientationService orientationService) {
        this.orientationService = orientationService;
    }

    // Get all Orientations
    @GetMapping
    public ResponseEntity<List<Orientation>> getAllOrientations() {
        List<Orientation> orientations = orientationService.getAllOrientations();
        return new ResponseEntity<>(orientations, HttpStatus.OK);
    }

    // Get an Orientation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Orientation> getOrientationById(@PathVariable Long id) {
        try {
            Orientation orientation = orientationService.getOrientationById(id);
            return new ResponseEntity<>(orientation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add a new Orientation
    @PostMapping
    public ResponseEntity<Orientation> saveOrientation(@RequestBody Orientation orientation) {
        Orientation savedOrientation = orientationService.saveOrientation(orientation);
        return new ResponseEntity<>(savedOrientation, HttpStatus.CREATED);
    }

    // Delete an Orientation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrientation(@PathVariable Long id) {
        try {
            orientationService.deleteOrientation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
