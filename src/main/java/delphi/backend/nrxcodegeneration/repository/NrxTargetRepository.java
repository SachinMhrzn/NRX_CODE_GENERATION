package delphi.backend.nrxcodegeneration.repository;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.NrxTarget;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface NrxTargetRepository extends CrudRepository<NrxTarget, Long> {

    /**
     * Find all NRX targets with a name that matches the given name
     *
     * @param name The name of the target.
     * @return A list of NrxTarget objects.
     */
    List<NrxTarget> findByName(String name);

    /**
     * Find all the targets with the given target names.
     *
     * @param targets a list of strings that are the names of the targets to find.
     * @return A list of NrxTarget objects.
     */
    List<NrxTarget> findByNameIn(List<String> targets);
}
