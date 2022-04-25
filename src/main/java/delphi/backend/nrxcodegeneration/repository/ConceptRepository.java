package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.Concept;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface ConceptRepository extends CrudRepository<Concept, Long> {

    /**
     * Find a concept by its concept.
     *
     * @param concept The concept to search for.
     * @return An Optional<NrxConcept>
     */
    Optional<Concept> findByConcept(String concept);


    /**
     * Returns a list of all the concepts in the database, ordered by concept name
     *
     * @return A list of NrxConcept objects.
     */
    List<Concept> listOrderByConcept();

    /**
     * Find all concepts that are in the given list of concepts
     *
     * @param concepts a list of strings representing the concepts
     * @return A list of NrxConcept objects.
     */
    List<Concept> findByConceptIn(List<String> concepts);
}
