package com.fst.il.m2.Projet.repositories;

import com.fst.il.m2.Projet.models.ResponsableFormation;
import com.fst.il.m2.Projet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface ResponsableFormationRepository extends JpaRepository<ResponsableFormation, Long> {
    @Modifying
    @Query("DELETE FROM ResponsableFormation r WHERE r.user = :user")
    void deleteByUser(@Param("user") User user);
}
