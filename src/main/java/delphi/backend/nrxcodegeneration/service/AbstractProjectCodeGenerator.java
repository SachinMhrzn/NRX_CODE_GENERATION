package delphi.backend.nrxcodegeneration.service;

import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.PersistenceException;

import delphi.backend.nrxcodegeneration.model.*;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import io.micronaut.http.server.exceptions.InternalServerException;
import delphi.backend.nrxcodegeneration.repository.ProjectCodeRepository;
import delphi.backend.nrxcodegeneration.interfaces.IProjectCodeGenerator;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

public abstract class AbstractProjectCodeGenerator implements IProjectCodeGenerator {

    @Inject
    private NrxUserService nrxUserService;

    @Inject
    private NrxTargetService nrxTargetService;

    @Inject
    private NrxProjectService nrxProjectService;

    @Inject
    private ProjectCodeRepository projectCodeRepository;

    /**
     * Generates a project code for a given project-code-dto.
     *
     * @param projectCodeDto The ProjectCodeDto object that contains the user, target and project name.
     * @return The project code.
     */
    public ProjectCodeDto generate(ProjectCodeDto projectCodeDto) {
        return this.getProjectCode(projectCodeDto);
    }

    /**
     * Create a new project code for a user, target, project and userPCode
     *
     * @param userId    The ChemRegUser who is creating the project code
     * @param projectId The project to which the code is being assigned
     * @param targetId  The target that the project code is for
     * @return The ProjectCodeEntity that was created.
     */
    public ProjectCodeEntity createNewProjectCode(
            Long userId,
            Long projectId,
            Long targetId,
            String userPCode
    ) {
        var projectCode = ProjectCodeEntity.builder()
                .personPId(userId)
                .projectPId(projectId)
                .userPCode(userPCode)
                .targetId(targetId)
                .build();

        try {
            var savedProjectCode = projectCodeRepository.save(projectCode);

            // Re-fetching the projectCodeEntity for `projectCode` field.
            return projectCodeRepository.findById(savedProjectCode.getProjectCodeID())
                    .orElseThrow(() -> new InternalServerException(String.format(NrxCodeConstant.NOT_FOUND, "Project Code")));
        } catch (PersistenceException persistenceException) {
            var errorMsg = persistenceException.getCause().getCause().getMessage();
            var preparedErrorMsg = getErrorMessage(errorMsg);

            if (preparedErrorMsg.isPresent()) {
                throw new BadRequestException(preparedErrorMsg.get());
            }
            throw new RuntimeException(errorMsg);
        }
    }

    /**
     * * Create a new ProjectCodeDto object and populate it with the values from the ProjectCodeEntity object and ProjectCodeDto.
     *
     * @param projectCode The ProjectCodeEntity object that is being mapped.
     * @param id          The ID for the project code dto.
     * @return The ProjectCodeDto object.
     */
    public ProjectCodeDto mapProjectCodeDto(ProjectCodeEntity projectCode, Long id) {
        return ProjectCodeDto.builder()
                .id(id)
                .projectCode(projectCode.getProjectCode())
                .projectCodeType(null)
                .build();
    }

    /**
     * Given a SQL exception message, return the error code and error message
     *
     * @param sqlExceptionErrorMsg The error message from the exception.
     * @return The error code and error message are being returned.
     */
    public Optional<String> getErrorMessage(String sqlExceptionErrorMsg) {
        var splitMsg = sqlExceptionErrorMsg.split("\n");
        var errorCode = NrxCodeConstant.MAP_ERROR_CODE_MESSAGE.keySet().stream()
                .filter(errorCodeKey -> splitMsg[0].contains(errorCodeKey)).findFirst();

        errorCode.ifPresent(NrxCodeConstant.MAP_ERROR_CODE_MESSAGE::get);
        return errorCode.map(NrxCodeConstant.MAP_ERROR_CODE_MESSAGE::get);
    }


    /**
     * Get project-code-dto with an existing project-code or a newly created one.
     *
     * @param projectCodeDto The ProjectCodeDto object that will be used to get the project code.
     * @return A ProjectCodeDto object.
     */
    abstract ProjectCodeDto getProjectCode(ProjectCodeDto projectCodeDto);
}
