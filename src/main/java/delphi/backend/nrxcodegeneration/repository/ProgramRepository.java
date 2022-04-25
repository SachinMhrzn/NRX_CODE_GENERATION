package delphi.backend.nrxcodegeneration.repository;

import java.util.List;
import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.Program;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface ProgramRepository extends CrudRepository<Program, Long> {

    /**
     * Find a NrxProgram by its program.
     *
     * @param program The program name.
     * @return A program object or null
     */
    Optional<Program> findByProgram(String program);


    /**
     * Find a NrxProgram by its program.
     *
     * @param programs The program names.
     * @return A program object or null
     */
    List<Program> findByProgramIn(List<String> programs);


    /**
     * Find the list of NrxProgram objects sorted by program name
     *
     * @return A list of NrxProgram objects.
     */
    List<Program> listOrderByProgram();
}
