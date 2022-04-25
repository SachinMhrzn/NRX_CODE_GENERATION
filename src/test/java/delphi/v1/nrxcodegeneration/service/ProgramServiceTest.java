package delphi.v1.nrxcodegeneration.service;

import java.util.List;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.ProgramService;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ProgramRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProgramServiceTest {

    private MetacodeMockDataService mockService = new MetacodeMockDataService();

    @InjectMocks
    private ProgramService programService;

    @Mock
    private ProgramRepository programRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for a valid and existing program name.
     */
    @Test
    void testProgramForValidAndExistingProgramName() {
        // Arrange
        var programName = "Nurix";
        var programs = mockService.getAllPrograms();
        var programNames = List.of(programName);

        when(programRepository.findByProgramIn(programNames)).thenReturn(programs);

        //Act
        var actualProgram = programService.findProgramByProgramNameThrowErrorIfNotFound(programs, programName);

        // Assert
        assertEquals(programName, actualProgram.getProgram());
    }

    /**
     * Test for a valid and existing program name.
     */
    @Test
    void testNrxProgramForInvalidAndNonExistingProgramName() {
        // Arrange
        var programName = "non-existing-program-name";
        var programs = mockService.getAllPrograms();
        var programNames = List.of(programName);

        when(programRepository.findByProgramIn(programNames)).thenReturn(programs);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> programService.findProgramByProgramNameThrowErrorIfNotFound(programs, programName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Program"), exception.getMessage());
    }
}
