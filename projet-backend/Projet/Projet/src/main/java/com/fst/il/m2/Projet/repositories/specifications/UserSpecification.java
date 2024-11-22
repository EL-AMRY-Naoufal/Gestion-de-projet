package com.fst.il.m2.Projet.repositories.specifications;

import com.fst.il.m2.Projet.enumurators.Role;
import com.fst.il.m2.Projet.models.Enseignant;
import com.fst.il.m2.Projet.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserSpecification {
    @PersistenceContext
    private  final EntityManager entityManager;
    public Specification<User> withRoleEnseignantAndNotInEnseignant() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            CriteriaQuery<Long> subQuery = criteriaBuilder.createQuery(Long.class);
            Root<Enseignant> enseignantRoot = subQuery.from(Enseignant.class);
            subQuery.select(enseignantRoot.get("user").get("id"));
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();

            Join<User, Role> roleJoin = root.join("roles");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(roleJoin, Role.ENSEIGNANT),
                    criteriaBuilder.not(root.get("id").in(ids))
            );
        };
    }

    public Specification<User> notInEnseignant() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);

            CriteriaQuery<Long> subQuery = criteriaBuilder.createQuery(Long.class);
            Root<Enseignant> enseignantRoot = subQuery.from(Enseignant.class);
            subQuery.select(enseignantRoot.get("user").get("id"));
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();

            return criteriaBuilder.not(root.get("id").in(ids));
        };
    }

    public Specification<User> withoutRoleEnseignant() {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            CriteriaQuery<Long> subQuery = criteriaBuilder.createQuery(Long.class);
            Root<User> userRoot = subQuery.from(User.class);
            /*subQuery.select(userRoot.get("id"));
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();*/

            Join<User, Role> roleJoin = userRoot.join("roles");
            return
                    criteriaBuilder.notEqual(roleJoin, Role.ENSEIGNANT);


        };
    }

    public  Specification<User> withRoleEnseignant() {
        return (root, query, criteriaBuilder) ->{
            query.distinct(true);

            CriteriaQuery<Long> subQuery = criteriaBuilder.createQuery(Long.class);
            Root<Enseignant> enseignantRoot = subQuery.from(Enseignant.class);
            subQuery.select(enseignantRoot.get("user").get("id"));
            List<Long> ids = entityManager.createQuery(subQuery).getResultList();
            return criteriaBuilder.in(root.get("id")).value(ids);

        };
    }
}

