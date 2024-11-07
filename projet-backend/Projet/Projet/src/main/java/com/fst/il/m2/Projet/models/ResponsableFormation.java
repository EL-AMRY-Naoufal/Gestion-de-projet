package com.fst.il.m2.Projet.models;

import com.fst.il.m2.Projet.enumurators.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ResponsableFormation")
public class ResponsableFormation extends User {

    public ResponsableFormation() {
    }

    public ResponsableFormation(String username, String password, String email, Role role) {
        super(username, password, email, role);
    }
}
