package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.enumurators.CategorieEnseignant;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long>, JpaSpecificationExecutor<Enseignant> {

    //findEnseignantIdByUserId
    @Query("SELECT e FROM Enseignant e WHERE e.user.id = :userId")
    Optional<Enseignant> findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Enseignant e WHERE e.user = :user")
    void deleteByUser(@Param("user") User user);
    List<Enseignant> findEnseignantByNameAndFirstname(String name, String firstname);
    //List<Enseignant> findEnseignantByFirstnameOrLastname(String name, String firstname);
    Optional <Enseignant> findByName(String name);
    Optional<Enseignant> findByFirstname(String firstname);

    @Query("SELECT u FROM Enseignant u WHERE  " +
            "LOWER(u.firstname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Enseignant> searchEnseignant(@Param("searchTerm") String searchTerm);
}
