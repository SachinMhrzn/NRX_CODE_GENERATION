package delphi.backend.nrxcodegeneration.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.backend.nrxcodegeneration.enums.ProjectCodeType;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ProjectCodeRepository;
import delphi.backend.nrxcodegeneration.repository.NrxUserPCodeRepository;
import delphi.backend.nrxcodegeneration.repository.AbstractProjectCodeRepository;

@Singleton
public class DELProjectCodeService extends AbstractProjectCodeGenerator {

    @Inject
    private ProjectCodeRepository projectCodeRepository;

    @Inject
    private NrxUserPCodeRepository nrxUserPCodeRepository;

    @Inject
    private AbstractProjectCodeRepository abstractProjectCodeRepository;

    /**
     * Get the project code for the given project-code-dto.
     *
     * @param projectCodeDto The project-code-dto
     * @return The project code dto.
     */
    @Override
    public ProjectCodeDto getProjectCode(ProjectCodeDto projectCodeDto) {
        var existingDelProjectCode = abstractProjectCodeRepository.fetchExistingDelAppendedProjectCode(
                projectCodeDto.getUserId(),
                projectCodeDto.getProjectId(),
                projectCodeDto.getTargetId()
        );

        if (existingDelProjectCode != null) {
            return super.mapProjectCodeDto(existingDelProjectCode, projectCodeDto.getId());
        }

        return createNewDelProjectCode(
                projectCodeDto.getUserId(),
                projectCodeDto.getTargetId(),
                projectCodeDto.getProjectId(),
                projectCodeDto.getId());
    }

    /**
     * Gets the monotonically incremented project code.
     *
     * @param projectCode Given projectCode to be monotonically increased.
     * @return Monotonically increased ProjectCode.
     */
    public String getMonotonicallyIncrementedProjectCode(String projectCode) {
        Integer result = Integer.parseInt(projectCode.substring(NrxCodeConstant.DEL_PREFIX.length()));

        return String.format(NrxCodeConstant.DEL_PREFIX + "%0" + NrxCodeConstant.PROJECT_CODE_MIN_NUMBER
                + "d", result + NrxCodeConstant.PROJECT_CODE_INCREMENT_FACTOR);
    }

    /**
     * Creates a new del-project code for the userId, target and project.
     *
     * @param userId           The ID of the user who is creating the project code
     * @param targetId         The ID of target of the project code.
     * @param projectId        The ID of project that the userId
     * @param projectCodeDtoId The ID for the project code Dto
     * @return The ProjectCodeDto object that was created.
     */
    private ProjectCodeDto createNewDelProjectCode(Long userId, Long targetId, Long projectId, Long projectCodeDtoId) {
        var latestProjectCode = nrxUserPCodeRepository.fetchLatestDelAppendedProjectCode();

        String newProjectCode = NrxCodeConstant.STARTING_DEL_PROJECT_CODE;
        if (!latestProjectCode.isEmpty()) {
            newProjectCode = getMonotonicallyIncrementedProjectCode(latestProjectCode.get(0).getUserPCode());
        }

        var projectCodeEntity = super.createNewProjectCode(userId, projectId, targetId, newProjectCode);

        return super.mapProjectCodeDto(projectCodeEntity, projectCodeDtoId);
    }

    /**
     * Returns true if the project code type is NORMAL
     *
     * @param projectCodeType The type of project code to check.
     * @return The method isQualified returns a Boolean value.
     */
    @Override
    public Boolean isQualified(ProjectCodeType projectCodeType) {
        return projectCodeType.equals(ProjectCodeType.DEL_APPENDED);
    }
}
