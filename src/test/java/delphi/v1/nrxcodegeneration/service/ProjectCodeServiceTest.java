package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.service.ProjectCodeService;
import delphi.backend.nrxcodegeneration.repository.NrxUserRepository;
import delphi.v1.nrxcodegeneration.data.NrxProjectCodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.NrxTargetRepository;
import delphi.backend.nrxcodegeneration.repository.NrxProjectRepository;
import delphi.backend.nrxcodegeneration.service.NormalProjectCodeService;
import delphi.backend.nrxcodegeneration.service.ProjectCodeServiceProvider;
import delphi.backend.nrxcodegeneration.model.ProjectCodeGenerationResponse;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ProjectCodeServiceTest {

    private final NrxProjectCodeMockDataService mockService = new NrxProjectCodeMockDataService();

    @Mock
    private NrxTargetRepository nrxTargetRepository;

    @Mock
    private NrxUserRepository nrxUserRepository;

    @Mock
    private NrxProjectRepository nrxProjectRepository;

    @Mock
    private ProjectCodeServiceProvider projectCodeServiceProvider;

    @Mock
    private NormalProjectCodeService normalProjectCodeService;

    @InjectMocks
    private ProjectCodeService projectCodeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }


    /**
     * Mock the repository using the mock service.
     */
    private void prepareMockServices() {
        var users = mockService.getAllNrxUsers();
        var projects = mockService.getAllNrxProjects();
        var targets = mockService.getAllNrxTargets();

        // Mock Services
        when(nrxUserRepository.findByUsernameIn(anyList())).thenReturn(users);
        when(nrxTargetRepository.findByNameIn(anyList())).thenReturn(targets);
        when(nrxProjectRepository.findByNameIn(anyList())).thenReturn(projects);

    }

    /**
     * Test for valid project-code-dto.
     */
    @Test
    void testProjectCodeGenerationForValidProjectCodeDto() {
        // Arrange
        var projectCodeDto = mockService.getValidProjectCodeDto();

        var expectedProjectCode = mockService.saveAndGetNewProjectCodeEntity();
        var expectedProjectCodeDto = mockService.mapProjectCodeDto(expectedProjectCode);

        when(projectCodeServiceProvider.find(any())).thenReturn(normalProjectCodeService);
        when(normalProjectCodeService.generate(any())).thenReturn(expectedProjectCodeDto);

        prepareMockServices();

        // Act
        var actualProjectCodeDto = projectCodeService.generateProjectCode(projectCodeDto);

        // Assert
        assertEquals(expectedProjectCodeDto.getProjectCode(), actualProjectCodeDto.getProjectCode());
    }

    /**
     * Test for invalid project-code-dto having invalid username.
     */
    @Test
    void testProjectCodeGenerationForProjectCodeDtoWithInvalidUsername() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoWithInvalidUsername();

        prepareMockServices();

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> projectCodeService.generateProjectCode(projectCodeDto));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "User"), exception.getMessage());
    }

    /**
     * Test for invalid project-code-dto having invalid project name.
     */
    @Test
    void testProjectCodeGenerationForProjectCodeDtoWithInvalidProjectName() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoWithInvalidProjectName();

        prepareMockServices();

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> projectCodeService.generateProjectCode(projectCodeDto));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Project"), exception.getMessage());
    }

    /**
     * Test for invalid project-code-dto having invalid target name.
     */
    @Test
    void testProjectCodeGenerationForProjectCodeDtoWithInvalidTargetName() {
        // Arrange
        var projectCodeDto = mockService.getProjectCodeDtoWithInvalidTargetName();

        prepareMockServices();

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> projectCodeService.generateProjectCode(projectCodeDto));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Target"), exception.getMessage());
    }


    /**
     * Test project code generation for all valid list of project code dto.
     */
    @Test
    void testProjectCodeGenerationForAllValidListOfProjectCodeDto() {
        // Arrange
        var projectCodeDtoList = mockService.getAllValidProjectCodeDtoList();
        var projectCodeDto = mockService.getProjectCodeDtoForNewProjectCode();

        when(projectCodeServiceProvider.find(any())).thenReturn(normalProjectCodeService);
        when(normalProjectCodeService.generate(any())).thenReturn(projectCodeDto);

        prepareMockServices();

        // Expected Value
        var expectedErrorFlag = false;
        var expectedSizeOfFailedGeneration = 0;
        var expectedSizeOfSucceededGeneration = projectCodeDtoList.size();

        // Act
        var actualProjectCodeGenerationResponse = projectCodeService.generateProjectCodesInBulk(projectCodeDtoList);

        // Assert
        assertProjectCodeDtoResponseEqual(
                expectedErrorFlag,
                expectedSizeOfFailedGeneration,
                expectedSizeOfSucceededGeneration,
                actualProjectCodeGenerationResponse
        );
    }

    /**
     * Test project code generation for all invalid list of project code dto.
     */
    @Test
    void testProjectCodeGenerationForAllInvalidListOfProjectCodeDto() {
        // Arrange
        var projectCodeDtoList = mockService.getAllInvalidProjectCodeDtoList();
        var projectCodeDto = mockService.getProjectCodeDtoForNewProjectCode();

        when(projectCodeServiceProvider.find(any())).thenReturn(normalProjectCodeService);
        when(normalProjectCodeService.generate(any())).thenReturn(projectCodeDto);

        prepareMockServices();

        // Expected Value
        var expectedErrorFlag = true;
        var expectedSizeOfFailedGeneration = mockService.getAllInvalidProjectCodeDtoList().size();
        var expectedSizeOfSucceededGeneration = 0;

        // Act
        var actualProjectCodeGenerationResponse = projectCodeService.generateProjectCodesInBulk(projectCodeDtoList);

        // Assert
        assertProjectCodeDtoResponseEqual(
                expectedErrorFlag,
                expectedSizeOfFailedGeneration,
                expectedSizeOfSucceededGeneration,
                actualProjectCodeGenerationResponse
        );
    }

    /**
     * Test project code generation for some valid list of project code dto.
     */
    @Test
    void testProjectCodeGenerationForSomeValidListOfProjectCodeDto() {
        // Arrange
        var projectCodeDtoList = mockService.getAllProjectCodeDtoList();
        var projectCodeDto = mockService.getValidProjectCodeDto();

        when(projectCodeServiceProvider.find(any())).thenReturn(normalProjectCodeService);
        when(normalProjectCodeService.generate(any())).thenReturn(projectCodeDto);

        prepareMockServices();

        // Expected Value
        var expectedErrorFlag = true;
        var expectedSizeOfFailedGeneration = mockService.getAllInvalidProjectCodeDtoList().size();
        var expectedSizeOfSucceededGeneration = mockService.getAllValidProjectCodeDtoList().size();

        // Act
        var actualProjectCodeGenerationResponse = projectCodeService.generateProjectCodesInBulk(projectCodeDtoList);

        // Assert
        assertProjectCodeDtoResponseEqual(
                expectedErrorFlag,
                expectedSizeOfFailedGeneration,
                expectedSizeOfSucceededGeneration,
                actualProjectCodeGenerationResponse
        );
    }

    /**
     * Asserts that the actual project code generation response is equal to the expected project code generation response
     *
     * @param expectedErrorFlag                   The boolean flag that indicates whether the generation of project code was successful or not.
     * @param expectedSizeOfFailedGeneration      The number of project codes that were expected to fail.
     * @param expectedSizeOfSucceededGeneration   The number of project codes that should have been generated.
     * @param actualProjectCodeGenerationResponse The actual response from the server.
     */
    private void assertProjectCodeDtoResponseEqual(
            Boolean expectedErrorFlag,
            Integer expectedSizeOfFailedGeneration,
            Integer expectedSizeOfSucceededGeneration,
            ProjectCodeGenerationResponse actualProjectCodeGenerationResponse
    ) {
        assertEquals(expectedErrorFlag, actualProjectCodeGenerationResponse.getError());
        assertEquals(expectedSizeOfSucceededGeneration, actualProjectCodeGenerationResponse.getSucceeded().size());
        assertEquals(expectedSizeOfFailedGeneration, actualProjectCodeGenerationResponse.getFailed().size());
    }
}
