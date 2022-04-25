package delphi.backend.nrxcodegeneration.service;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.enums.ProjectCodeType;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.interfaces.IProjectCodeGenerator;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class ProjectCodeServiceProvider {

    private Set<IProjectCodeGenerator> projectCodeGenerators;

    @Inject
    public ProjectCodeServiceProvider(Set<IProjectCodeGenerator> projectCodeGenerators) {
        this.projectCodeGenerators = projectCodeGenerators;
    }

    /**
     * Find the first service that qualifies the project code type
     *
     * @param projectCodeType The project code type to find a service for.
     * @return The project-code generator that qualifies for the given metacode.
     */
    public IProjectCodeGenerator find(ProjectCodeType projectCodeType) {
        return projectCodeGenerators
                .stream()
                .filter(service -> service.isQualified(projectCodeType))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(NrxCodeConstant.PROJECT_CODE_SERVICE_NOT_FOUND));
    }
}
