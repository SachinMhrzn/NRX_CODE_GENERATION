package delphi.backend.nrxcodegeneration.repository;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.NrxProject;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface NrxProjectRepository extends CrudRepository<NrxProject, Long> {

    /**
     * Find all nrx projects with a given name.
     *
     * @param name The name of the project to search for.
     * @return A list of NrxProject objects.
     */
    List<NrxProject> findByName(String name);

    /**
     * Find all the projects with the given project names.
     *
     * @param projects a list of strings that are the names of the projects to find.
     * @return A list of Projects objects.
     */
    List<NrxProject> findByNameIn(List<String> projects);
}
