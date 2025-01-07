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
                "sec", Role.SECRETARIAT_PEDAGOGIQUE
        );

        /*for(UserRole ur : userRoles.values()){
            userRoleRepository.findAllByRoleAndYear(ur.getRole(), ur.getYear());
        }*/

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<User> users = List.of(
                User.builder().username("cdd").password(passwordEncoder.encode("cdd")).email("cdd@cdd.fr").roles(new ArrayList<>()).build(),
                User.builder().username("rdf").password(passwordEncoder.encode("rdf")).email("rdf@rdf.fr").roles(new ArrayList<>()).build(),
                User.builder().username("ens").password(passwordEncoder.encode("ens")).email("ens@ens.fr").roles(new ArrayList<>()).build(),
                User.builder().username("sec").password(passwordEncoder.encode("sec")).email("sec@sec.fr").roles(new ArrayList<>()).build()
        );
        for(User u : users){
            u.addRole(1L, userRoles.get(u.getUsername()));
            userRepository.findUserByEmail(u.getEmail()).orElseGet(() -> userRepository.save(u));
        }

        anneeRepository.findById(1L).orElseGet(() -> anneeRepository.save(Annee.builder().id(1L).debut(2024).build()));



        //data to create : Annee -> Orientation -> Niveau -> ...?

        /**********USERS**********/
        //users enseignants
        ArrayList<UserRole> rolesEnseignant1 = new ArrayList<>();
        rolesEnseignant1.add(new UserRole(1L,  Role.ENSEIGNANT, 2025L, null));
        User user1 = new User(5L, "username1", "password1", "email1@email.fr", rolesEnseignant1);
        user1.getRoles().forEach((role) -> role.setUser(user1));

        ArrayList<UserRole> rolesEnseignant2 = new ArrayList<>();
        rolesEnseignant2.add(new UserRole(2L,  Role.ENSEIGNANT, 2025L, null));
        User user2 = new User(6L, "username2", "password2", "email2@email.fr", rolesEnseignant2);
        user2.getRoles().forEach((role) -> role.setUser(user2));

        //users rdf
        ArrayList<UserRole> rolesRDF1 = new ArrayList<>();
        rolesRDF1.add(new UserRole(3L, Role.RESPONSABLE_DE_FORMATION, 2025L, null));
        User user3 = new User(7L, "username3", "password3", "email3@email.fr", rolesRDF1);
        user3.getRoles().forEach((role) -> role.setUser(user3));

        ArrayList<UserRole> rolesRDF2 = new ArrayList<>();
        rolesRDF2.add(new UserRole(3L, Role.RESPONSABLE_DE_FORMATION, 2025L, null));
        User user4 = new User(8L, "username4", "password4", "email4@email.fr", rolesRDF2);
        user4.getRoles().forEach((role) -> role.setUser(user4));

        /**********RESPONSABLE FORMATIONS**********/
        ResponsableFormation responsableFormation1 = new ResponsableFormation(1L, user3);
        ResponsableFormation responsableFormation2 = new ResponsableFormation(2L, user4);

        /**********FORMATIONS**********/
        Formation formation1 = new Formation(1L, "formation 1", 200, responsableFormation1,null);
        Formation formation2 = new Formation(2L, "formation 2", 200, responsableFormation2,null);

        /**********ENSEIGNANTS**********/
        Map<CategorieEnseignant, Integer> heuresRequises = new HashMap<>();
        heuresRequises.put(CategorieEnseignant.PROFESSEUR, 100);
        Enseignant enseignant1 = new Enseignant(1L, heuresRequises, 100, 70, null, user1);
        Enseignant enseignant2 = new Enseignant(2L, heuresRequises, 100, 30, null, user2);

        /**********AFFECTATIONS**********/
        LocalDate date = LocalDate.of(2020, 1, 8);

        List<Affectation> affectations1 = new ArrayList<>();
        affectations1.add(new Affectation(1L, 40, date, enseignant1, null));
        affectations1.add(new Affectation(2L, 20, date, enseignant2, null));

        List<Affectation> affectations2 = new ArrayList<>();
        affectations2.add(new Affectation(3L, 30, date, enseignant1, null));
        affectations2.add(new Affectation(4L, 10, date, enseignant2, null));


        /**********TYPES D'HEURES**********/
        Map<TypeHeure, Integer> heuresParTypesM1 = new HashMap<>(Map.of());
        heuresParTypesM1.put(TypeHeure.CM, 30);
        heuresParTypesM1.put(TypeHeure.TD, 20);
        heuresParTypesM1.put(TypeHeure.TP, 10);
        Map<TypeHeure, Integer> heuresParTypesM2 = new HashMap<>(Map.of());
        heuresParTypesM2.put(TypeHeure.CM, 40);
        heuresParTypesM2.put(TypeHeure.TD, 30);
        heuresParTypesM2.put(TypeHeure.TP, 20);

        /**********GROUPES**********/
        Groupe groupe1 = new Groupe(1L, "groupe 1", new Date(2024, Calendar.DECEMBER,1), TypeHeure.CM, null);
        Groupe groupe2 = new Groupe(2L, "groupe 2", new Date(2024, Calendar.DECEMBER,1), TypeHeure.TD, null);
        Groupe groupe3 = new Groupe(3L, "groupe 3", new Date(2024, Calendar.DECEMBER,1), TypeHeure.CM, null);

        ArrayList<Groupe> groupes1 = new ArrayList<>();
        groupes1.add(groupe1);
        groupes1.add(groupe2);
        ArrayList<Groupe> groupes2 = new ArrayList<>();
        groupes2.add(groupe3);
        /**********MODULES**********/
        List<com.fst.il.m2.Projet.models.Module> modules1 = List.of(
                new com.fst.il.m2.Projet.models.Module(1L, "module 1", 60, heuresParTypesM1, formation1, affectations1, null)
        );
        List<com.fst.il.m2.Projet.models.Module> modules2 = List.of(
                new com.fst.il.m2.Projet.models.Module(2L, "module 2", 40, heuresParTypesM2, formation2, affectations2, null)
        );

        //update groupes
        groupe1.setModule(modules1.get(0));
        groupe2.setModule(modules1.get(0));
        groupe3.setModule(modules2.get(0));

        //save users
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

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

        //save enseignants
        enseignantRepository.save(enseignant1);
        enseignantRepository.save(enseignant2);

        //save Affectations
        affectations1.forEach((a) -> affectationRepository.save(a));
        affectations2.forEach((a) -> affectationRepository.save(a));

        //update Enseignants
        ArrayList<Affectation> affectationsEnseignant1 = new ArrayList<>();
        affectationsEnseignant1.add(affectations1.get(0));
        affectationsEnseignant1.add(affectations2.get(0));
        enseignant1.setAffectations(affectationsEnseignant1);

        ArrayList<Affectation> affectationsEnseignant2 = new ArrayList<>();
        affectationsEnseignant2.add(affectations1.get(1));
        affectationsEnseignant2.add(affectations2.get(1));
        enseignant2.setAffectations(affectationsEnseignant2);

        //save modules //TODO must create groupes before

        System.out.println("MODUUUUUULLLLLEEEE : " + modules1.get(0).getGroupes());
        System.out.println("GROUPE : " + groupe1.getModule());
        moduleRepository.save(modules1.get(0));
        moduleRepository.save(modules2.get(0));

        //update Formations
        formation1.setModules(modules1);
        formation2.setModules(modules2);

        //update Affectations
        affectations1.get(0).setModule(modules1.get(0));
        affectations1.get(1).setModule(modules2.get(0));


        //save groupes //TODO must create modules before
        groupeRepository.save(groupe1);
        groupeRepository.save(groupe2);
        groupeRepository.save(groupe3);

        //update modules
        modules1.get(0).setGroupes(groupes1);
        modules2.get(0).setGroupes(groupes2);





        /**/

    }
}
