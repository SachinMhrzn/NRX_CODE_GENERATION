package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import javax.inject.Named;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;

import delphi.backend.nrxcodegeneration.util.CriteriaUtil;
import delphi.backend.nrxcodegeneration.model.ProjectCodeEntity;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public abstract class AbstractProjectCodeRepository
        implements CrudRepository<ProjectCodeEntity, String> {

    private final EntityManager entityManager;

    public AbstractProjectCodeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Fetch the project code for a project that has been DEL appended prefix.
     *
     * @param userId    The user's ID
     * @param projectId The project ID
     * @param targetId  The ID of the target
     * @return The project code of the first project code that matches the criteria.
     */
    public ProjectCodeEntity fetchExistingDelAppendedProjectCode(Long userId, Long projectId, Long targetId) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(ProjectCodeEntity.class);
        var root = criteriaQuery.from(ProjectCodeEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        CriteriaUtil.addSingleLikePredicate(root, criteriaBuilder, predicates, "userPCode", "DEL%");

        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "personPId", userId);
        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "targetId", targetId);
        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "projectPId", projectId);
        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "active", "yes");

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var em = entityManager.createQuery(criteriaQuery);
        em.setFirstResult(0);
        em.setMaxResults(1);

        var resultList = em.getResultList();

        return resultList.stream().findFirst().orElse(null);
    }

    /**
     * Fetch an existing project code for a project code.
     *
     * @param userId    The user's ID
     * @param projectId The project ID
     * @param targetId  The ID of the target
     * @return The project code of the first project code that matches the criteria.
     */
    public ProjectCodeEntity fetchExistingProjectCode(Long userId, Long projectId, Long targetId) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(ProjectCodeEntity.class);
        var root = criteriaQuery.from(ProjectCodeEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "personPId", userId);
        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "targetId", targetId);
        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "projectPId", projectId);
        CriteriaUtil.addEqualPredicate(root, criteriaBuilder, predicates, "active", "yes");

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var em = entityManager.createQuery(criteriaQuery);
        em.setFirstResult(0);
        em.setMaxResults(1);

        var resultList = em.getResultList();

        return resultList.stream().findFirst().orElse(null);
    }
}
