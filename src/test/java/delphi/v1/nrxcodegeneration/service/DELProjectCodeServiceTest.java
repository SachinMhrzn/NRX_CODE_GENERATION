package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.*;
import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.v1.nrxcodegeneration.data.NrxProjectCodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.ProjectCodeRepository;
import delphi.backend.nrxcodegeneration.repository.NrxUserPCodeRepository;
import delphi.backend.nrxcodegeneration.repository.AbstractProjectCodeRepository;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;


class DELProjectCodeServiceTest {

    private NrxProjectCodeMockDataService mockService = new NrxProjectCodeMockDataService();

    @Mock
    private NrxUserService nrxUserService;

    @Mock
    private NrxTargetService nrxTargetService;

    @Mock
    private ProjectCodeRepository projectCodeRepository;

    @Mock
    private AbstractProjectCodeRepository abstractProjectCodeRepository;

    @Mock
    private NrxUserPCodeRepository nrxUserPCodeRepository;

    @Mock
    private NrxProjectService nrxProjectService;

    @InjectMocks
    private DELProjectCodeService delProjectCodeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Given a ProjectCodeDto, prepare the data and get the expected ProjectCodeDto and mock services
     *
     * @param projectCodeDto The ProjectCodeDto.
     * @return The expected ProjectCodeDto.
     */
    private ProjectCodeDto prepareDataAndGetExpectedProjectCodeDtoAndMockServices(ProjectCodeDto projectCodeDto) {

        var nrxUser = mockService.getNrxUserByUsername(projectCodeDto.getUsername());
        var nrxTarget = mockService.getNrxTargetByName(projectCodeDto.getTargetName());
        var nrxProject = mockService.getNrxProjectByName(projectCodeDto.getProjectName());

        var newProjectCodeEntity = mockService.saveAndGetNewDELProjectCodeEntity();
        var optionalProjectCodeEntity = mockService.findProjectEntityById(newProjectCodeEntity);
        var existingProjectCodeEntity = mockService.fetchExistingDelAppendedProjectCode(nrxUser.getId(), nrxProject.getId(), nrxTarget.getId());

        // Mock Services and Repositories
        when(nrxUserService.findNrxUserByUsernameThrowErrorIfNotFound(anyString())).thenReturn(nrxUser);
        when(nrxTargetService.findNrxTargetByNameThrowErrorIfNotFound(anyString())).thenReturn(nrxTarget);
        when(nrxProjectService.findNrxProjectByNameThrowErrorIfNotFound(anyString())).thenReturn(nrxProject);

        when(projectCodeRepository.save(any())).thenReturn(newProjectCodeEntity);
        when(projectCodeRepository.findById(anyLong())).thenReturn(optionalProjectCodeEntity);
        when(abstractProjectCodeRepository.fetchExistingDelAppendedProjectCode(anyLong(), anyLong(), anyLong())).thenReturn(existingProjectCodeEntity);

        var projectCodeEntity = existingProjectCodeEntity != null ? existingProjectCodeEntity : newProjectCodeEntity;

        return mockService.mapProjectCodeDto(projectCodeEntity);
    }

    /**
     * Tests the generation of existing project code for DEL project code for a valid user, target and project.
     */
    @Test
    void testExistingDelProjectCodeGenerationForValidUserTargetAndProject() {
        // Arrange
        var projectCodeDto = mockService.getDELProjectCodeDtoForValidUserTargetAndProject();
        var expectedProjectCodeDto = prepareDataAndGetExpectedProjectCodeDtoAndMockServices(projectCodeDto);

        // Act
        var actualProjectCodeDto = delProjectCodeService.generate(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }

    /**
     * Tests the generation of new project code for DEL project code for a valid user, target and project.
     */
    @Test
    void testNewDelProjectCodeGenerationForValidUserProjectAndTarget() {
        // Arrange
        var projectCodeDto = mockService.getDELProjectCodeDtoForNewDELProjectCode();
        var expectedProjectCodeDto = prepareDataAndGetExpectedProjectCodeDtoAndMockServices(projectCodeDto);

        // Act
        var actualProjectCodeDto = delProjectCodeService.generate(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }
}
