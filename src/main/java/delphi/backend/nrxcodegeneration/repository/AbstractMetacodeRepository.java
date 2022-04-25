package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import javax.inject.Named;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.transaction.annotation.TransactionalAdvice;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;

import delphi.backend.nrxcodegeneration.model.Metacode;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public abstract class AbstractMetacodeRepository
        implements CrudRepository<Metacode, String> {

    private final EntityManager entityManager;

    public AbstractMetacodeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Fetch an existing metacode from the database
     *
     * @param metacode The NrxMetacode object that we want to fetch.
     * @return The first metacode that matches the criteria.
     */
    public Metacode fetchExistingMetacode(Metacode metacode) {

        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Metacode.class);
        var root = criteriaQuery.from(Metacode.class);

        List<Predicate> predicates = new ArrayList<>();

        setPredicatesForMetacode(root, criteriaBuilder, predicates, metacode);

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        var em = entityManager.createQuery(criteriaQuery);
        em.setFirstResult(0);
        em.setMaxResults(1);

        var resultList = em.getResultList();

        return resultList.stream().findFirst().orElse(null);
    }

    /**
     * Set the predicates for the metacode
     *
     * @param root            The root of the query. This is the starting point of the query.
     * @param criteriaBuilder The CriteriaBuilder instance.
     * @param predicates      The list of predicates that will be added to the criteria.
     * @param metacode        The NrxMetacode object that we are searching for.
     */
    private void setPredicatesForMetacode(
            Root<Metacode> root,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates,
            Metacode metacode
    ) {
        addEqualOrIsNullPredicate(metacode.getProgramId(), "programId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getContractId(), "contractId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getProgramId(), "programId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getContractId(), "contractId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getConceptId(), "conceptId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getVariantId(), "variantId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getTargetOneId(), "targetOneId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getTargetTwoId(), "targetTwoId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getSeriesId(), "seriesId", root, criteriaBuilder, predicates);
        addEqualOrIsNullPredicate(metacode.getDelLineage(), "delLineage", root, criteriaBuilder, predicates);
    }

    /**
     * Add an equality predicate to the list of predicates if the field value is not null, otherwise, add isNull predicate.
     *
     * @param fieldValue The value of the field to be used in the predicate.
     * @param fieldName  The name of the field in the entity.
     * @param root       The root of the query.
     * @param builder    The CriteriaBuilder instance.
     * @param predicates The list of predicates that will be added to the CriteriaBuilder.
     */
    private <T> void addEqualOrIsNullPredicate(
            T fieldValue,
            String fieldName,
            Root<Metacode> root,
            CriteriaBuilder builder,
            List<Predicate> predicates
    ) {
        var predicate = fieldValue != null
                ? builder.equal(root.get(fieldName).as(String.class), String.valueOf(fieldValue))
                : builder.isNull(root.get(fieldName));

        predicates.add(predicate);
    }
}
