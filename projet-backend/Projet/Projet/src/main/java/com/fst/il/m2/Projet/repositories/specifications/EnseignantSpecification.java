package com.fst.il.m2.Projet.repositories.specifications;

import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EnseignantSpecification {
    @PersistenceContext
    private  final EntityManager entityManager;

    public Specification<Enseignant> getEnseignantWithUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
             criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public Specification<Enseignant> byNameandFirstname(String name, String firstname) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            CriteriaQuery<Long> subQuery = criteriaBuilder.createQuery(Long.class);

            Predicate namePredicate = criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("name")), criteriaBuilder.lower(criteriaBuilder.literal(name))
            );
            Predicate firstnamePredicate = criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("firstname")), criteriaBuilder.lower(criteriaBuilder.literal(firstname))
            );
            Predicate userPredicateNull = criteriaBuilder.isNull(
                            root.get("user")
            );

            return criteriaBuilder.and(namePredicate, firstnamePredicate, userPredicateNull);
        };
    }
}
