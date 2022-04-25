package delphi.v1.nrxcodegeneration.service;

import java.util.ArrayList;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.service.NrxTargetService;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.v1.nrxcodegeneration.data.NrxProjectCodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.NrxTargetRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NrxTargetServiceTest {

    private final NrxProjectCodeMockDataService mockService = new NrxProjectCodeMockDataService();
    private final MetacodeMockDataService metacodeMockService = new MetacodeMockDataService();

    @InjectMocks
    private NrxTargetService nrxTargetService;

    @Mock
    private NrxTargetRepository nrxTargetRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for valid and existing target name.
     */
    @Test
    void testNrxTargetForValidAndExistingTargetName() {
        // Arrange
        var validTargetName = "Mpro";
        var nrxTargetList = mockService.findNrxTargetByTargetName(validTargetName);

        when(nrxTargetRepository.findByName(anyString())).thenReturn(nrxTargetList);

        // Act
        var actualTarget = nrxTargetService.findNrxTargetByNameThrowErrorIfNotFound(anyString());

        // Assert
        assertEquals(validTargetName, actualTarget.getName());
    }

    /**
     * Test for invalid and non-existing target name.
     */
    @Test
    void testNrxTargetForInvalidAndNonExistingTargetName() {
        // Arrange
        when(nrxTargetRepository.findByName(anyString())).thenReturn(new ArrayList<>());

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> nrxTargetService.findNrxTargetByNameThrowErrorIfNotFound(anyString()));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Target"), exception.getMessage());
    }

    /**
     * Test for valid and existing targetOne name.
     */
    @Test
    void testNrxTargetOneForValidAndExistingTargetOneName() {
        // Arrange
        var targetOneName = "BTK";
        var targetOneList = metacodeMockService.getAllNrxTargets();

        when(nrxTargetRepository.findByNameIn(any())).thenReturn(targetOneList);

        // Act
        var actualTarget = nrxTargetService.findNrxTargetByTargetOneThrowErrorIfNotFound(targetOneList,
                targetOneName);

        // Assert
        assertEquals(targetOneName, actualTarget.getName());
    }

    /**
     * Test for invalid and non-existing targetOne name.
     */
    @Test
    void testNrxTargetOneForInvalidAndNonExistingTargetOneName() {
        // Arrange
        var targetOneName = "non-existing-targetOne-name";
        var targetOneList = metacodeMockService.getAllNrxTargets();

        when(nrxTargetRepository.findByNameIn(any())).thenReturn(targetOneList);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> nrxTargetService.findNrxTargetByTargetOneThrowErrorIfNotFound(targetOneList, targetOneName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Target 1"), exception.getMessage());
    }

    /**
     * Test for valid and existing targetTwo name.
     */
    @Test
    void testNrxTargetTwoForValidAndExistingTargetTwoName() {
        // Arrange
        var targetTwoName = "Mpro";
        var targetTwoList = metacodeMockService.getAllNrxTargets();

        when(nrxTargetRepository.findByNameIn(any())).thenReturn(targetTwoList);

        // Act
        var actualTarget = nrxTargetService.findNrxTargetByTargetTwoThrowErrorIfInvalid(targetTwoList,
                targetTwoName);

        // Assert
        assertEquals(targetTwoName, actualTarget.getName());
    }

    /**
     * Test for targetTwo being null.
     */
    @Test
    void testNrxTargetTwoForNullTargetTwoName() {
        // Arrange
        String targetTwoName = null;
        var targetTwoList = metacodeMockService.getAllNrxTargets();

        when(nrxTargetRepository.findByNameIn(any())).thenReturn(targetTwoList);

        // Act
        var actualTarget = nrxTargetService.findNrxTargetByTargetTwoThrowErrorIfInvalid(targetTwoList,
                targetTwoName);

        // Assert
        assertEquals(targetTwoName, actualTarget);
    }

    /**
     * Test for invalid or non-existing targetTwo name.
     */
    @Test
    void testNrxTargetTwoForInvalidAndNonExistingTargetTwoName() {
        // Arrange
        var targetTwoName = "non-existing-targetTwo-name";
        var targetTwoList = metacodeMockService.getAllNrxTargets();

        when(nrxTargetRepository.findByNameIn(any())).thenReturn(targetTwoList);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> nrxTargetService.findNrxTargetByTargetTwoThrowErrorIfInvalid(targetTwoList, targetTwoName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Target 2"), exception.getMessage());
    }
}
