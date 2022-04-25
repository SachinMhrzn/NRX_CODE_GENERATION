package delphi.v1.nrxcodegeneration.service;

import java.util.List;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.ConceptService;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ConceptRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConceptServiceTest {

    private MetacodeMockDataService mockService = new MetacodeMockDataService();

    @InjectMocks
    private ConceptService conceptService;

    @Mock
    private ConceptRepository conceptRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for a valid and existing concept name.
     */
    @Test
    void testConceptForValidAndExistingConceptName() {
        // Arrange
        var conceptName = "Collaborator";
        var conceptList = mockService.getAllConcepts();
        var conceptNames = List.of(conceptName);

        when(conceptRepository.findByConceptIn(conceptNames)).thenReturn(conceptList);

        // Act
        var actualConcept = conceptService.findConceptByConceptNameThrowErrorIfNotFound(conceptList, conceptName);

        // Assert
        assertEquals(conceptName, actualConcept.getConcept());
    }

    /**
     * Test for invalid and non-existing concept name.
     */
    @Test
    void testConceptForInvalidAndNonExistingConceptName() {
        // Arrange
        var conceptName = "non-existing-concept-name";
        var conceptList = mockService.getAllConcepts();
        var conceptNames = List.of(conceptName);

        when(conceptRepository.findByConceptIn(conceptNames)).thenReturn(conceptList);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> conceptService.findConceptByConceptNameThrowErrorIfNotFound(conceptList, conceptName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Concept"), exception.getMessage());
    }
}
