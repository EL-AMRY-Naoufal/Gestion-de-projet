package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.models.Orientation;

import java.util.List;

public interface OrientationService {
    Orientation saveOrientation(Orientation orientation);
    Orientation getOrientationById(Long id);
    List<Orientation> getAllOrientations();
    void deleteOrientation(Long id);
}
