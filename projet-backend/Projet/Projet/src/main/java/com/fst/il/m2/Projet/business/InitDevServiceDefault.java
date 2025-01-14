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
    OrientationRepository orientationRepository;
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
                "username3", Role.RESPONSABLE_DE_FORMATION,
                "username4", Role.RESPONSABLE_DE_FORMATION

        );

        /*for(UserRole ur : userRoles.values()){
            userRoleRepository.findAllByRoleAndYear(ur.getRole(), ur.getYear());
        }*/

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = List.of(
                User.builder().username("cdd").password(passwordEncoder.encode("cdd")).email("cdd@cdd.fr").roles(new ArrayList<>()).build(),
                User.builder().username("rdf").password(passwordEncoder.encode("rdf")).email("rdf@rdf.fr").roles(new ArrayList<>()).build(),
                User.builder().username("ens").password(passwordEncoder.encode("ens")).email("ens@ens.fr").roles(new ArrayList<>()).build(),
                User.builder().username("sec").password(passwordEncoder.encode("sec")).email("sec@sec.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username1").password(passwordEncoder.encode("password1")).email("email1@email.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username2").password(passwordEncoder.encode("password2")).email("email2@email.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username3").password(passwordEncoder.encode("password3")).email("email3@email.fr").roles(new ArrayList<>()).build(),
                User.builder().username("username4").password(passwordEncoder.encode("password4")).email("email4@email.fr").roles(new ArrayList<>()).build()
                );
        for(User u : users){
            u.addRole(1L, userRoles.get(u.getUsername()));
            userRepository.findUserByEmail(u.getEmail()).orElseGet(() -> userRepository.save(u));
        }

        Annee annee = Annee.builder().id(1L).debut(2024).build();
        anneeRepository.findById(1L).orElseGet(() -> anneeRepository.save(annee));

        //TODO frontend user roles do not work when we add a year. (in "get userRoles()" function from login.service.ts)
//        Annee annee2025 = Annee.builder().debut(2025).build();
//        anneeRepository.save(annee2025);



        //data to create : Annee -> Departement -> Formation -> Niveau -> Orientation -> Semestre -> Modules -> Groupe



        /**********RESPONSABLE FORMATIONS**********/
        ResponsableFormation responsableFormation1 = new ResponsableFormation(1L, users.get(6));
        ResponsableFormation responsableFormation2 = new ResponsableFormation(2L, users.get(7));

        /**********FORMATIONS**********/
        Formation formation1 = new Formation(1L, "Licence", 200, responsableFormation1,null);
        Formation formation2 = new Formation(2L, "Master", 200, responsableFormation2,null);
        ArrayList<Formation> formations = new ArrayList<>();
        formations.add(formation1);
        formations.add(formation2);

        /**********NIVEAU**********/
//        Niveau M2 = Niveau.builder().nom("M2").formation(formation1).build(); //add orientation
        Niveau M2 = new Niveau(1L, "M2", formation2, null);
        /**********ORIENTATION**********/
        Orientation IL = Orientation.builder().nom("IL").niveau(M2).build(); //add semestres

        /**********SEMESTRE**********/
        Semestre S1 = Semestre.builder().nom("S1").orientation(IL).build(); //add modules
        Semestre S2 = Semestre.builder().nom("S2").orientation(IL).build(); //add modules

        /*********RESPONSABLE DEPARTEMENT*********/
        ResponsableDepartement responsableDepartement = ResponsableDepartement.builder().user(users.get(0)).build();

        /*********DEPARTEMENTS*********/
        Departement departement = Departement.builder().nom("INFORMATIQUE").annee(annee).formations(formations).responsableDepartement(responsableDepartement).build();

        /**********ENSEIGNANTS**********/
        Map<CategorieEnseignant, Integer> heuresRequises = new HashMap<>();
        heuresRequises.put(CategorieEnseignant.PROFESSEUR, 100);
        Enseignant enseignant1 = new Enseignant(1L, heuresRequises, 100, 70, null, users.get(4));
        Enseignant enseignant2 = new Enseignant(2L, heuresRequises, 100, 30, null, users.get(5));

        /**********TYPES D'HEURES**********/
        Map<TypeHeure, Integer> heuresParTypesM1 = new HashMap<>(Map.of());
        heuresParTypesM1.put(TypeHeure.CM, 30);
        heuresParTypesM1.put(TypeHeure.TD, 20);
        heuresParTypesM1.put(TypeHeure.TP, 10);
        Map<TypeHeure, Integer> heuresParTypesM2 = new HashMap<>(Map.of());
        heuresParTypesM2.put(TypeHeure.CM, 40);
        heuresParTypesM2.put(TypeHeure.TD, 30);
        heuresParTypesM2.put(TypeHeure.TP, 20);

        /**********MODULES**********/
        List<com.fst.il.m2.Projet.models.Module> modules1 = List.of(
                new com.fst.il.m2.Projet.models.Module(1L, "module 1", 60, heuresParTypesM1, formation1, null)
        );
        List<com.fst.il.m2.Projet.models.Module> modules2 = List.of(
                new com.fst.il.m2.Projet.models.Module(2L, "module 2", 40, heuresParTypesM2, formation2, null)
        );

        /**********GROUPES**********/
//        Groupe groupe1 = Groupe.builder().nom("groupe 1").date(new Date(2024, Calendar.DECEMBER,1)).type(TypeHeure.CM).build();
//        Groupe groupe2 = Groupe.builder().nom("groupe 2").date(new Date(2024, Calendar.DECEMBER,1)).type(TypeHeure.TD).build();
//        Groupe groupe3 = Groupe.builder().nom("groupe 3").date(new Date(2024, Calendar.DECEMBER,1)).type(TypeHeure.CM).build();

        Groupe groupe1 = new Groupe(1L, "Groupe 1", new Date(2024, Calendar.DECEMBER, 1), TypeHeure.CM, null, null);
        Groupe groupe2 = new Groupe(2L, "Groupe 2", new Date(2024, Calendar.DECEMBER, 1), TypeHeure.TD, null, null);
        Groupe groupe3 = new Groupe(3L, "Groupe 3", new Date(2024, Calendar.DECEMBER, 1), TypeHeure.CM, null, null);
        ArrayList<Groupe> groupes1 = new ArrayList<>();
        groupes1.add(groupe1);
        groupes1.add(groupe2);
        ArrayList<Groupe> groupes2 = new ArrayList<>();
        groupes2.add(groupe3);

        /**********AFFECTATIONS**********/
        LocalDate date = LocalDate.of(2025, 1, 1);

        List<Affectation> affectations1 = new ArrayList<>();
        affectations1.add(new Affectation(1L, 40, date, enseignant1, groupe1)); //add groupes
        affectations1.add(new Affectation(2L, 20, date, enseignant2, groupe1));

        List<Affectation> affectations2 = new ArrayList<>();
        affectations2.add(new Affectation(3L, 30, date, enseignant1, groupe2));
        affectations2.add(new Affectation(4L, 10, date, enseignant2, groupe2));

        List<Affectation> affectations3 = new ArrayList<>();
        affectations3.add(new Affectation(5L, 60, date, enseignant1, groupe3));
        affectations3.add(new Affectation(6L, 20, date, enseignant2, groupe3));

        //update groupes
        groupe1.setModule(modules1.get(0));
        groupe2.setModule(modules1.get(0));
        groupe3.setModule(modules2.get(0));

        long time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        //WAIT FOR USERS TO BE SAVED
        while(userRepository.findById(8L).isEmpty())
            if (time > TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 10)
                break;

        userRepository.findAll().forEach((user)-> System.out.println(user.getId()));

        //NOW USERS SHOULD BE SAVED

        //save RDF
        responsableFormationRepository.save(responsableFormation1);
        responsableFormationRepository.save(responsableFormation2);

        //save formations
        formationRepository.save(formation1);
        formationRepository.save(formation2);

        //save niveau
        niveauRepository.save(M2);

        //save orientation
        orientationRepository.save(IL);

        //save semestres
        semestreRepository.save(S1);
        semestreRepository.save(S2);

        //save RDD
        responsableDepartementRepository.save(responsableDepartement);

        //save Departement
        departementRepository.save(departement);

        //save enseignants
        enseignantRepository.save(enseignant1);
        enseignantRepository.save(enseignant2);

        //save modules
        moduleRepository.save(modules1.get(0));
        moduleRepository.save(modules2.get(0));

        //save groupes
        groupeRepository.save(groupe1);
        groupeRepository.save(groupe2);
        groupeRepository.save(groupe3);

        //save Affectations
        affectations1.forEach((a) -> affectationRepository.save(a));
        affectations2.forEach((a) -> affectationRepository.save(a));
        affectations3.forEach((a) -> affectationRepository.save(a));

        //update Enseignants
        ArrayList<Affectation> affectationsEnseignant1 = new ArrayList<>();
        affectationsEnseignant1.add(affectations1.get(0));
        affectationsEnseignant1.add(affectations2.get(0));
        affectationsEnseignant1.add(affectations3.get(0));
        enseignant1.setAffectations(affectationsEnseignant1);

        ArrayList<Affectation> affectationsEnseignant2 = new ArrayList<>();
        affectationsEnseignant2.add(affectations1.get(1));
        affectationsEnseignant2.add(affectations2.get(1));
        affectationsEnseignant2.add(affectations3.get(1));
        enseignant2.setAffectations(affectationsEnseignant2);

        //update Formations
        formation1.setModules(modules1);
        formation2.setModules(modules2);

        //WAIT FOR GROUPES TO BE SAVED
        time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        while(groupeRepository.findById(3L).isEmpty())
            if (time > TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 10)
                break;

        //NOW USERS SHOULD BE SAVED

        //update Groupe
        groupe1.setAffectations(affectations1);
        groupe2.setAffectations(affectations2);
        groupe3.setAffectations(affectations3);

        groupeRepository.save(groupe1);
        groupeRepository.save(groupe2);
        groupeRepository.save(groupe3);

        //update modules
        modules1.get(0).setGroupes(groupes1);
        modules2.get(0).setGroupes(groupes2);

        moduleRepository.save(modules1.get(0));
        moduleRepository.save(modules2.get(0));
    }
}
