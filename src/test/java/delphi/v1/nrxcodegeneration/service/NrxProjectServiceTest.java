package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.service.NrxProjectService;
import delphi.v1.nrxcodegeneration.data.NrxProjectCodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.NrxProjectRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NrxProjectServiceTest {

    private NrxProjectCodeMockDataService mockService = new NrxProjectCodeMockDataService();

    @InjectMocks
    private NrxProjectService nrxProjectService;

    @Mock
    private NrxProjectRepository nrxProjectRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for valid and existing project name.
     */
    @Test
    void testNrxProjectForValidAndExistingProjectName() {
        // Arrange
        var validProjectName = "BTK";
        var nrxProjectList = mockService.findNrxProjectsByProjectName(validProjectName);

        when(nrxProjectRepository.findByName(anyString())).thenReturn(nrxProjectList);

        //Act
        var actualProject = nrxProjectService.findNrxProjectByNameThrowErrorIfNotFound(anyString());

        // Assert
        assertEquals(validProjectName, actualProject.getName());
    }

    /**
     * Test for invalid project name.
     */
    @Test
    void testNrxProjectForInvalidAndNonExistingProjectName() {
        // Arrange
        var invalidProjectName = "invalid-project-name";
        var nrxProjectList = mockService.findNrxProjectsByProjectName(invalidProjectName);

        when(nrxProjectRepository.findByName(anyString())).thenReturn(nrxProjectList);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> nrxProjectService.findNrxProjectByNameThrowErrorIfNotFound(anyString()));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Project"), exception.getMessage());
    }
}
