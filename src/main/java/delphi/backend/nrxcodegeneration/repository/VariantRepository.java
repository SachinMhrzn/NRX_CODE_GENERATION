package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.Variant;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface VariantRepository extends CrudRepository<Variant, Long> {

    /**
     * Find a variant by its variant name
     *
     * @param variant The name of the variant to find.
     * @return An Optional<NrxVariant>
     */
    Optional<Variant> findByVariant(String variant);

    /**
     * Find the list of all variants sorted by variant
     *
     * @return A list of NrxVariant objects.
     */
    List<Variant> listOrderByVariant();

    /**
     * Find all the variants that are in the list of variants
     *
     * @param variants a list of variant names
     * @return A list of NrxVariant objects.
     */
    List<Variant> findByVariantIn(List<String> variants);
}
