package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Orientation;
import com.fst.il.m2.Projet.repositories.OrientationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrientationServiceDefault implements OrientationService {

    @Autowired
    private  OrientationRepository orientationRepository;

    @Override
    public Orientation saveOrientation(Orientation orientation) {
        return orientationRepository.save(orientation);
    }

    @Override
    public Orientation getOrientationById(Long id) {
        return orientationRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Orientation> getAllOrientations() {
        return orientationRepository.findAll();
    }

    @Override
    public void deleteOrientation(Long id) {
        orientationRepository.deleteById(id);
    }
}
