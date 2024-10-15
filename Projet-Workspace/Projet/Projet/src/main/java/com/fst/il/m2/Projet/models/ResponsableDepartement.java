package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ResponsableDepartement")
public class ResponsableDepartement extends User{

    public ResponsableDepartement() {
    }

    public ResponsableDepartement(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }
}
