package delphi.backend.nrxcodegeneration.util;

import java.util.List;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;

public class CriteriaUtil {

    /**
     * Add SQL LIKE predicate for the given property list associated with the property name.
     *
     * @param root       The root of the query.
     * @param builder    The CriteriaBuilder instance.
     * @param predicates The list of predicates that will be added to the criteria.
     * @param path       The path to the field in the entity that you want to filter on.
     * @param value      The value of the filter.
     */
    public static <X, E, Y> void addSingleLikePredicate(
            From<X, Y> root,
            CriteriaBuilder builder,
            List<Predicate> predicates,
            String path,
            E value
    ) {
        if (value == null) {
            return;
        }

        var predicate = builder.like(root.get(path).as(String.class), String.valueOf(value));

        predicates.add(predicate);
    }

    /**
     * Add SQL EQUAL predicate for the given property list associated with the property name.
     *
     * @param root       The root of the query.
     * @param builder    The CriteriaBuilder instance.
     * @param predicates The list of predicates that will be added to the criteria.
     * @param path       The path to the field in the entity that we want to filter on.
     * @param value      The value of the filter.
     */
    public static <X, E, Y> void addEqualPredicate(
            From<X, Y> root,
            CriteriaBuilder builder,
            List<Predicate> predicates,
            String path,
            E value
    ) {
        if (value == null) {
            return;
        }

        var predicate = builder.equal(root.get(path).as(String.class), String.valueOf(value));

        predicates.add(predicate);
    }
}
