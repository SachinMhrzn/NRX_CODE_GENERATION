package delphi.backend.nrxcodegeneration.repository;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.model.ProjectCodeEntity;

@Repository
public interface ProjectCodeRepository extends CrudRepository<ProjectCodeEntity, Long> {

    /**
     * Find all the ProjectCodeEntity objects that have a personPId of :personPId and a projectPId of :projectPId and a
     * targetId of :targetId
     *
     * @param personPId  The id for the user.
     * @param projectPId the id for the project.
     * @param targetId   The id for the target.
     * @return A list of ProjectCodeEntity objects.
     */
    List<ProjectCodeEntity> findByPersonPIdAndProjectPIdAndTargetId(Long personPId, Long projectPId, Long targetId);

    /**
     * Find all the ProjectCodeEntity objects that have a personPId of personPId, a projectPId of projectPId.
     *
     * @param personPId  The id for the user.
     * @param projectPId the id for the project.
     * @return A list of ProjectCodeEntity objects.
     */
    List<ProjectCodeEntity> findByPersonPIdAndProjectPId(Long personPId, Long projectPId);
}
