package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.NrxUserRepository;
import delphi.backend.nrxcodegeneration.repository.NrxTargetRepository;
import delphi.backend.nrxcodegeneration.repository.NrxProjectRepository;
import delphi.backend.nrxcodegeneration.model.ProjectCodeGenerationResponse;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;
import delphi.backend.nrxcodegeneration.foundation.error.exception.MetacodeGenerationException;

@Singleton
public class ProjectCodeService {

    @Inject
    private NrxUserRepository nrxUserRepository;

    @Inject
    private NrxTargetRepository nrxTargetRepository;

    @Inject
    private NrxProjectRepository nrxProjectRepository;

    @Inject
    private ProjectCodeServiceProvider projectCodeServiceProvider;

    /**
     * Generates a project code.
     *
     * @param projectCodeDto The ProjectCodeDto object that contains the information about the project code.
     * @return ProjectCodeDto.
     */
    @Transactional
    public ProjectCodeDto generateProjectCode(ProjectCodeDto projectCodeDto) {
        validateAndSetIdForUserProjectAndTarget(List.of(projectCodeDto));

        if (projectCodeDto.getMessage() != null) {
            throw new BadRequestException(projectCodeDto.getMessage());
        }

        var projectCodeService = projectCodeServiceProvider.find(projectCodeDto.getProjectCodeType());

        return projectCodeService.generate(projectCodeDto);
    }

    /**
     * This function takes a list of ProjectCodeDto objects and generates a ProjectCodeGenerationResponse object.
     *
     * @param projectCodeDtoList List<ProjectCodeDto>
     * @return The metacodeGenerationResponse object contains the metacode and the error messages.
     */
    @Transactional
    public ProjectCodeGenerationResponse generateProjectCodesInBulk(List<ProjectCodeDto> projectCodeDtoList) {
        validateAndSetIdForUserProjectAndTarget(projectCodeDtoList);

        var invalidProjectCodeList = projectCodeDtoList
                .stream()
                .filter(metacodeDto -> metacodeDto.getMessage() != null)
                .collect(Collectors.toList());
        projectCodeDtoList.removeAll(invalidProjectCodeList);

        var validMetacodeDtoList = new ArrayList<ProjectCodeDto>();

        projectCodeDtoList.forEach(projectCodeDto -> {
            try {
                var projectCodeService = projectCodeServiceProvider.find(projectCodeDto.getProjectCodeType());
                var projectCodeDtoWithCode = projectCodeService.generate(projectCodeDto);

                validMetacodeDtoList.add(projectCodeDtoWithCode);
            } catch (MetacodeGenerationException | BadRequestException exception) {
                projectCodeDto.setMessage(exception.getMessage());

                invalidProjectCodeList.add(projectCodeDto);
            }
        });

        return ProjectCodeGenerationResponse.builder()
                .succeeded(validMetacodeDtoList)
                .failed(invalidProjectCodeList)
                .error(!invalidProjectCodeList.isEmpty())
                .build();
    }

    /**
     * * Validate and set the project code dto
     * * Find the corresponding user, project and target.
     * * Set the corresponding ids in the userId, projectId and targetId
     *
     * @param projectCodeDtoList The list of metacodeDto objects that to validate.
     */
    private void validateAndSetIdForUserProjectAndTarget(List<ProjectCodeDto> projectCodeDtoList) {
        var targetNames = projectCodeDtoList.stream().map(ProjectCodeDto::getTargetName).collect(Collectors.toList());
        var targets = nrxTargetRepository.findByNameIn(targetNames);

        var projectNames = projectCodeDtoList.stream().map(ProjectCodeDto::getProjectName).collect(Collectors.toList());
        var projects = nrxProjectRepository.findByNameIn(projectNames);

        var usernames = projectCodeDtoList.stream().map(ProjectCodeDto::getUsername).collect(Collectors.toList());
        var users = nrxUserRepository.findByUsernameIn(usernames);

        projectCodeDtoList.forEach(projectCodeDto -> {
            try {
                var user = users.stream()
                        .filter(nrxUser -> nrxUser.getUsername().equals(projectCodeDto.getUsername()))
                        .findFirst()
                        .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "User")));

                var target = targets.stream()
                        .filter(nrxTarget -> nrxTarget.getName().equals(projectCodeDto.getTargetName()))
                        .findFirst()
                        .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Target")));

                var project = projects.stream()
                        .filter(nrxProject -> nrxProject.getName().equals(projectCodeDto.getProjectName()))
                        .findFirst()
                        .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Project")));

                projectCodeDto.setUserId(user.getId());
                projectCodeDto.setTargetId(target.getId());
                projectCodeDto.setProjectId(project.getId());
            } catch (BadRequestException exception) {
                projectCodeDto.setMessage(exception.getMessage());
            }
        });
    }
}
