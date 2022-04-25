package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;

import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.MetacodeRepository;
import delphi.backend.nrxcodegeneration.service.InhibitorMetacodeService;
import delphi.backend.nrxcodegeneration.repository.AbstractMetacodeRepository;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class InhibitorMetacodeServiceTest {

    private final MetacodeMockDataService mockService = new MetacodeMockDataService();

    @Mock
    private MetacodeRepository metacodeRepository;

    @Mock
    private AbstractMetacodeRepository abstractMetacodeRepository;

    @InjectMocks
    private InhibitorMetacodeService inhibitorMetacodeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test inhibitor metacode generation for valid meta data.
     */
    @Test
    void testInhibitorMetacodeGenerationForValidMetadata() {
        // Arrange
        var metacodeDto = mockService.getInhibitorMetacodeDto();
        var expectedMetacode = mockService.getNewMetacode();

        when(metacodeRepository.save(any())).thenReturn(expectedMetacode);

        //Act
        var actualMetacode = inhibitorMetacodeService.generateMetacode(metacodeDto);

        // Assert
        assertEquals(expectedMetacode.getMetacodeId(), actualMetacode.getMetacodeId());
    }

    /**
     * Test the qualification of Inhibitor metacode generation service for inhibitor metacode dto.
     */
    @Test
    void testInhibitorMetacodeGenerationForQualificationOfInhibitorMetacodeDto() {
        // Arrange
        var expectedValue = true;
        var metacodeDto = mockService.getInhibitorMetacodeDto();

        //Act
        var isQualified = inhibitorMetacodeService.isQualified(metacodeDto);

        // Assert
        assertEquals(expectedValue, isQualified);
    }

    /**
     * Test the qualification of Inhibitor metacode generation service for non-inhibitor metacode dto.
     */
    @Test
    void testInhibitorMetacodeGenerationForQualificationOfNonInhibitorMetacodeDto() {
        // Arrange
        var expectedValue = false;
        var metacodeDto = mockService.getDelMetacodeDto();

        //Act
        var isQualified = inhibitorMetacodeService.isQualified(metacodeDto);

        // Assert
        assertEquals(expectedValue, isQualified);
    }
}
