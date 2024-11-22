package com.fst.il.m2.Projet.repositories.specifications;

import com.fst.il.m2.Projet.models.Enseignant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EnseignantSpecification {
    @PersistenceContext
    private  final EntityManager entityManager;

    public Specification<Enseignant> getEnseignantWithUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
             criteriaBuilder.equal(root.get("user").get("id"), userId);
    }
}
