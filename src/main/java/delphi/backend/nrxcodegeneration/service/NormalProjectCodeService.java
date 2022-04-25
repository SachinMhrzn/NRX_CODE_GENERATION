package delphi.backend.nrxcodegeneration.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.backend.nrxcodegeneration.enums.ProjectCodeType;
import delphi.backend.nrxcodegeneration.repository.AbstractProjectCodeRepository;

@Singleton
public class NormalProjectCodeService extends AbstractProjectCodeGenerator {

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
        var projectCode = abstractProjectCodeRepository.fetchExistingProjectCode(
                projectCodeDto.getUserId(),
                projectCodeDto.getProjectId(),
                projectCodeDto.getTargetId()
        );

        if (projectCode == null) {
            projectCode = super.createNewProjectCode(
                    projectCodeDto.getUserId(),
                    projectCodeDto.getProjectId(),
                    projectCodeDto.getTargetId(),
                    projectCodeDto.getUserPCode()
            );
        }

        return super.mapProjectCodeDto(projectCode, projectCodeDto.getId());
    }

    /**
     * Returns true if the project code type is NORMAL
     *
     * @param projectCodeType The type of project code to check.
     * @return The method isQualified returns a Boolean value.
     */
    @Override
    public Boolean isQualified(ProjectCodeType projectCodeType) {
        return projectCodeType.equals(ProjectCodeType.NORMAL);
    }
}
