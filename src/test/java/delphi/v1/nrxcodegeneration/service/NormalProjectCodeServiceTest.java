package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.*;
import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.v1.nrxcodegeneration.data.NrxProjectCodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.ProjectCodeRepository;
import delphi.backend.nrxcodegeneration.repository.AbstractProjectCodeRepository;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NormalProjectCodeServiceTest {

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
    private NrxProjectService nrxProjectService;

    @InjectMocks
    private NormalProjectCodeService normalProjectCodeService;

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

        var newProjectCodeEntity = mockService.saveAndGetNewProjectCodeEntity();
        var optionalProjectCodeEntity = mockService.findProjectEntityById(newProjectCodeEntity);
        var existingProjectCodeEntity = mockService.findProjectEntityByUserTargetAndProject(nrxUser, nrxProject, nrxTarget);

        // Mock Services and Repositories
        when(nrxUserService.findNrxUserByUsernameThrowErrorIfNotFound(anyString())).thenReturn(nrxUser);
        when(nrxTargetService.findNrxTargetByNameThrowErrorIfNotFound(anyString())).thenReturn(nrxTarget);
        when(nrxProjectService.findNrxProjectByNameThrowErrorIfNotFound(anyString())).thenReturn(nrxProject);

        when(projectCodeRepository.save(any())).thenReturn(newProjectCodeEntity);
        when(projectCodeRepository.findById(anyLong())).thenReturn(optionalProjectCodeEntity);
        when(abstractProjectCodeRepository.fetchExistingProjectCode(anyLong(), anyLong(), anyLong())).thenReturn(existingProjectCodeEntity);

        var projectCodeEntity = existingProjectCodeEntity != null ? existingProjectCodeEntity : newProjectCodeEntity;

        return mockService.mapProjectCodeDto(projectCodeEntity);
    }

    /**
     * Tests the generation of existing project code for normal project code for a valid user, target and project.
     */
    @Test
    void testExistingNormalProjectCodeGenerationForValidUserTargetAndProject() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoForValidUserTargetAndProject();
        var expectedProjectCodeDto = prepareDataAndGetExpectedProjectCodeDtoAndMockServices(projectCodeDto);

        // Act
        var actualProjectCodeDto = normalProjectCodeService.generate(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }

    /**
     * Tests the generation of existing project code for normal project code for a valid user, target, project and userPCode.
     */
    @Test
    void testExistingNormalProjectCodeGenerationForValidUserTargetAndProjectWithUserPCode() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoForValidUserTargetAndProjectWithUserPCode();
        var expectedProjectCodeDto = prepareDataAndGetExpectedProjectCodeDtoAndMockServices(projectCodeDto);

        // Act
        var actualProjectCodeDto = normalProjectCodeService.generate(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }

    /**
     * Tests the generation of new project code for normal project code for a valid user, target, project.
     */
    @Test
    void testNewNormalProjectCodeGenerationForValidUserProjectAndTarget() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoForNewProjectCode();
        var expectedProjectCodeDto = prepareDataAndGetExpectedProjectCodeDtoAndMockServices(projectCodeDto);

        // Act
        var actualProjectCodeDto = normalProjectCodeService.generate(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }

    /**
     * Tests the generation of new project code for normal project code for a valid user, target, project with userPCode.
     */
    @Test
    void testNewNormalProjectCodeGenerationForValidUserProjectWithUserPCode() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoForNewProjectCodeWithUserPCode();
        var expectedProjectCodeDto = prepareDataAndGetExpectedProjectCodeDtoAndMockServices(projectCodeDto);

        // Act
        var actualProjectCodeDto = normalProjectCodeService.generate(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }
}
