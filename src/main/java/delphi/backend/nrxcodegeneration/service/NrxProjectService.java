package delphi.backend.nrxcodegeneration.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.NrxProject;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.NrxProjectRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class NrxProjectService {

    @Inject
    private NrxProjectRepository nrxProjectRepository;

    /**
     * Find nrx project by name and throw an error if not found.
     *
     * @param projectName The name of the project to find.
     * @return NrxProject.
     * @throws BadRequestException If the project with given projectName is not found.
     */
    public NrxProject findNrxProjectByNameThrowErrorIfNotFound(String projectName) {
        return nrxProjectRepository.findByName(projectName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Project")));
    }
}
