package com.fst.il.m2.Projet.business;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.enumurators.TypeHeure;
import com.fst.il.m2.Projet.models.*;
import com.fst.il.m2.Projet.repositories.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fst.il.m2.Projet.models.Module;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class InitDevServiceDefault implements InitDevService {

    @Autowired
    FormationRepository formationRepository;
    @Autowired
    AffectationRepository affectationRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    EnseignantRepository enseignantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ResponsableFormationRepository responsableFormationRepository;
    @Autowired
    GroupeRepository groupeRepository;
    @Autowired
    AnneeRepository anneeRepository;
    @Autowired
    ResponsableDepartementRepository responsableDepartementRepository;
    @Autowired
    DepartementRepository departementRepository;
    @Autowired
    NiveauRepository niveauRepository;
    @Autowired
    SemestreRepository semestreRepository;

    @PostConstruct
    public void postConstruct() {
        /*
            Comptes de tests
         */

        // UserRoles
        Map<String, Role> userRoles = Map.of(
                "cdd", Role.CHEF_DE_DEPARTEMENT,
                "rdf", Role.RESPONSABLE_DE_FORMATION,
                "ens", Role.ENSEIGNANT,
                "sec", Role.SECRETARIAT_PEDAGOGIQUE,
                "username1", Role.ENSEIGNANT,
                "username2", Role.ENSEIGNANT,
                "username3", Role.ENSEIGNANT,
                "username4", Role.RESPONSABLE_DE_FORMATION
        );

        /*for(UserRole ur : userRoles.values()){
            userRoleRepository.findAllByRoleAndYear(ur.getRole(), ur.getYear());
        }*/

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = List.of(
                User.builder().username("cdd").firstname("Chef").name("FST").password(passwordEncoder.encode("cdd")).email("cdd@cdd.fr").roles(new ArrayList<>()).build(),
                User.builder().username("rdf").firstname("Responsable").name("IL").password(passwordEncoder.encode("rdf")).email("rdf@rdf.fr").roles(new ArrayList<>()).build(),
                User.builder().username("ens").password(passwordEncoder.encode("ens")).email("ens@ens.fr").roles(new ArrayList<>()).build(),
                User.builder().username("sec").password(passwordEncoder.encode("sec")).email("sec@sec.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username1").firstname("Emmanuel").name("Jeandel").password(passwordEncoder.encode("password1")).email("email1@email.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username2").firstname("Horatiu").name("Cirstea").password(passwordEncoder.encode("password2")).email("email2@email.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username3").firstname("Sokolov").name("Dmitry").password(passwordEncoder.encode("password3")).email("email3@email.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username4").firstname("Marie").name("Duflot Kremer").password(passwordEncoder.encode("password4")).email("email4@email.fr").roles(new ArrayList<>()).build()
                );
        Annee annee = Annee.builder().id(1L).debut(2024).build();
        anneeRepository.findById(1L).orElseGet(() -> anneeRepository.save(annee));

        for(User u : users){
            u.addRole(annee, userRoles.get(u.getUsername()));
            userRepository.findUserByEmail(u.getEmail()).orElseGet(() -> userRepository.save(u));
        }


        //TODO frontend user roles do not work when we add a year. (in "get userRoles()" function from login.service.ts)
//        Annee annee2025 = Annee.builder().debut(2025).build();
//        anneeRepository.save(annee2025);



        //data to create : Annee -> Departement -> Formation -> Niveau -> Orientation -> Semestre -> Modules -> Groupe



        ///********RESPONSABLE FORMATIONS
        ResponsableFormation responsableFormation1 = new ResponsableFormation(1L, users.get(6));
        ResponsableFormation responsableFormation2 = new ResponsableFormation(2L, users.get(7));

        responsableFormationRepository.save(responsableFormation1);
        responsableFormationRepository.save(responsableFormation2);

        ///*******RESPONSABLE DEPARTEMENT
        ResponsableDepartement responsableDepartement = ResponsableDepartement.builder().user(users.get(0)).build();

        responsableDepartementRepository.save(responsableDepartement);

        ///*******DEPARTEMENTS
        Departement departement = Departement.builder().nom("INFORMATIQUE").annee(annee).responsableDepartement(responsableDepartement).build();

        departementRepository.save(departement);

        ///********FORMATIONS
        Formation formation1 = Formation.builder().id(1L).nom("Licence").totalHeures(200).responsableFormation(responsableFormation1).departement(departement).build();
        Formation formation2 = Formation.builder().id(2L).nom("Master").totalHeures(200).responsableFormation(responsableFormation2).departement(departement).build();
        ArrayList<Formation> formations = new ArrayList<>();
        formations.add(formation1);
        formations.add(formation2);

        formationRepository.save(formation1);
        formationRepository.save(formation2);

        //update Formation
        departement.setFormations(formations);
        departementRepository.save(departement);

        ///********NIVEAU
        //        Niveau M2 = Niveau.builder().nom("M2").formation(formation1).build(); //add orientation
        Niveau M1 = new Niveau(1L, "M1", formation2, null);
        Niveau M2IL = new Niveau(1L, "M2 IL", formation2, null);

        niveauRepository.save(M1);
        niveauRepository.save(M2IL);

        ///********SEMESTRE
        Semestre S1 = Semestre.builder().nom("S1").niveau(M2IL).build(); //add modules
        Semestre S2 = Semestre.builder().nom("S2").niveau(M2IL).build(); //add modules
        List<Semestre> semestres =new ArrayList<>();
        semestres.add(S1);
        semestres.add(S2);

        semestreRepository.save(S1);
        semestreRepository.save(S2);

        //update Niveau
        M1.setSemestres(semestres);
        niveauRepository.save(M1);

        ///********ENSEIGNANTS
        Map<CategorieEnseignant, Integer> heuresRequises = new HashMap<>();
        heuresRequises.put(CategorieEnseignant.ENSEIGNANT_CHERCHEUR, 100);

        // Création des enseignants SANS leurs heures assignées
        Enseignant enseignant1 = new Enseignant(1L, users.get(4).getName(), users.get(4).getFirstname(),
                heuresRequises, 100, null, null, users.get(4), true);

        Enseignant enseignant2 = new Enseignant(2L, users.get(5).getName(), users.get(5).getFirstname(),
                heuresRequises, 100, null, null, users.get(5), true);

        Enseignant enseignant3 = new Enseignant(3L, users.get(6).getName(), users.get(6).getFirstname(),
                heuresRequises, 100, null, null, users.get(6), true);

// Création des heures assignées en les associant aux enseignants
        List<HeuresAssignees> heuresAnnee1 = List.of(
                HeuresAssignees.builder().annee(annee).heures(70).enseignant(enseignant1).build()
        );
        List<HeuresAssignees> heuresAnnee2 = List.of(
                HeuresAssignees.builder().annee(annee).heures(30).enseignant(enseignant2).build()
        );
        List<HeuresAssignees> heuresAnnee3 = List.of(
                HeuresAssignees.builder().annee(annee).heures(50).enseignant(enseignant3).build()
        );

// Mise à jour des enseignants avec leurs heures assignées
        enseignant1.setHeuresParAnnee(heuresAnnee1);
        enseignant2.setHeuresParAnnee(heuresAnnee2);
        enseignant3.setHeuresParAnnee(heuresAnnee3);

        enseignantRepository.save(enseignant1);
        enseignantRepository.save(enseignant2);
        enseignantRepository.save(enseignant3);

        ///********TYPES D'HEURES
        Map<TypeHeure, Integer> heuresParTypesM1 = new HashMap<>(Map.of());
        heuresParTypesM1.put(TypeHeure.CM, 60);
        heuresParTypesM1.put(TypeHeure.TD, 80);
        heuresParTypesM1.put(TypeHeure.TP, 50);
        Map<TypeHeure, Integer> heuresParTypesM2 = new HashMap<>(Map.of());
        heuresParTypesM2.put(TypeHeure.CM, 50);
        heuresParTypesM2.put(TypeHeure.TD, 70);
        heuresParTypesM2.put(TypeHeure.TP, 40);

        ///********MODULES
        List<com.fst.il.m2.Projet.models.Module> modules1 = List.of(
                Module.builder().nom("Service Web")./*totalHeuresRequises(60).*/heuresParType(heuresParTypesM1).semestre(S1).build(),
                Module.builder().nom("Concept Web")./*totalHeuresRequises(50).*/heuresParType(heuresParTypesM1).semestre(S1).build()

        );
        List<com.fst.il.m2.Projet.models.Module> modules2 = List.of(
                Module.builder().nom("Verification")./*totalHeuresRequises(40).*/heuresParType(heuresParTypesM2).semestre(S2).build(),
                Module.builder().nom("Modélisation")./*totalHeuresRequises(30).*/heuresParType(heuresParTypesM2).semestre(S2).build()
        );

        modules1.forEach(m -> moduleRepository.save(m));
        modules2.forEach(m -> moduleRepository.save(m));

        //update Semestres
        S1.setModules(modules1);
        S2.setModules(modules2);
        semestreRepository.save(S1);
        semestreRepository.save(S2);

        ///********GROUPES
        Groupe groupe1 = Groupe.builder()
                .nom("CM Groupe 1")
                .date(new Date(2024, Calendar.DECEMBER, 1))
                .type(TypeHeure.CM)
                .module(modules1.get(0))
                .totalHeuresDuGroupe(heuresParTypesM1.get(TypeHeure.CM))
                .build();

        Groupe groupe2 = Groupe.builder()
                .nom("TD Groupe 1")
                .date(new Date(2024, Calendar.DECEMBER, 1))
                .type(TypeHeure.TD)
                .module(modules1.get(0))
                .totalHeuresDuGroupe(heuresParTypesM1.get(TypeHeure.TD))
                .build();
//        Groupe groupe3 = Groupe.builder().nom("groupe 3").date(new Date(2024, Calendar.DECEMBER,1)).type(TypeHeure.CM).module(modules2.get(0)).build();

        ArrayList<Groupe> groupes1 = new ArrayList<>();
        groupes1.add(groupe1);
        groupes1.add(groupe2);

        ArrayList<Groupe> groupes2 = new ArrayList<>();
//        groupes2.add(groupe3);

        //save groupes
        groupeRepository.save(groupe1);
        groupeRepository.save(groupe2);
//        groupeRepository.save(groupe3);

        //update modules
        modules1.get(0).setGroupes(groupes1);
        modules2.get(0).setGroupes(groupes2);
        moduleRepository.save(modules1.get(0));
        moduleRepository.save(modules2.get(0));

        ///********AFFECTATIONS
        LocalDate date = LocalDate.of(2024, 1, 1);

        List<Affectation> affectations1 = new ArrayList<>();
        affectations1.add(Affectation.builder().heuresAssignees(40).dateAffectation(date).enseignant(enseignant1).groupe(groupe1).build()); //add groupes
        affectations1.add(Affectation.builder().heuresAssignees(20).dateAffectation(date).enseignant(enseignant2).groupe(groupe1).build()); //add groupes

        List<Affectation> affectations2 = new ArrayList<>();
        affectations2.add(Affectation.builder().heuresAssignees(30).dateAffectation(date).enseignant(enseignant1).groupe(groupe2).build()); //add groupes
        affectations2.add(Affectation.builder().heuresAssignees(10).dateAffectation(date).enseignant(enseignant2).groupe(groupe2).build()); //add groupes

        affectations1.forEach((a) -> affectationRepository.save(a));
        affectations2.forEach((a) -> affectationRepository.save(a));

        //update groupes
        groupe1.setModule(modules1.get(0));
        groupe2.setModule(modules1.get(0));

        groupeRepository.save(groupe1);
        groupeRepository.save(groupe2);

        //update enseignants
        ArrayList<Affectation> affectationsEnseignant1 = new ArrayList<>();
        affectationsEnseignant1.add(affectations1.get(0));
        affectationsEnseignant1.add(affectations2.get(0));
        enseignant1.setAffectations(affectationsEnseignant1);

        ArrayList<Affectation> affectationsEnseignant2 = new ArrayList<>();
        affectationsEnseignant2.add(affectations1.get(1));
        affectationsEnseignant2.add(affectations2.get(1));
        enseignant2.setAffectations(affectationsEnseignant2);

        //update Groupe
        groupe1.setAffectations(affectations1);
        groupe2.setAffectations(affectations2);

        groupeRepository.save(groupe1);
        groupeRepository.save(groupe2);
    }
}
