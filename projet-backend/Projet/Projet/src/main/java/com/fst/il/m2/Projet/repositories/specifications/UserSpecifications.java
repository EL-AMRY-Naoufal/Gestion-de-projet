package com.fst.il.m2.Projet.repositories.specifications;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserSpecifications {
    @PersistenceContext
    private  final EntityManager entityManager;
    public Specification<User> withRoleEnseignantAndNotInEnseignant() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            CriteriaQuery<Long> subQuery = criteriaBuilder.createQuery(Long.class);
            Root<Enseignant> enseignantRoot = subQuery.from(Enseignant.class);
            subQuery.select(enseignantRoot.get("id")); // Select the id from
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();

            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("role"), Role.ENSEIGNANT),
                    criteriaBuilder.not(root.get("id").in(ids))
            );
        };
    }
}

