package com.fst.il.m2.Projet.enumurators;

import lombok.Getter;

@Getter
public enum TypeHeure {
    CM(1.5), TD(1.0), TP(1.0), TPL(1.0), EI(1.0);

    private final double coef;
    TypeHeure(double coef) {
        this.coef = coef;
    }
}
