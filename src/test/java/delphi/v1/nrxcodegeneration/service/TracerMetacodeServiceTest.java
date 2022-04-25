package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;

import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.MetacodeRepository;
import delphi.backend.nrxcodegeneration.service.TracerMetacodeService;
import delphi.backend.nrxcodegeneration.repository.AbstractMetacodeRepository;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TracerMetacodeServiceTest {

    private final MetacodeMockDataService mockService = new MetacodeMockDataService();

    @Mock
    private MetacodeRepository metacodeRepository;

    @Mock
    private AbstractMetacodeRepository abstractMetacodeRepository;

    @InjectMocks
    private TracerMetacodeService tracerMetacodeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test Tracer metacode generation for valid meta data.
     */
    @Test
    void testTracerMetacodeGenerationForValidMetadata() {
        // Arrange
        var metacodeDto = mockService.getTracerMetacodeDto();
        var expectedMetacode = mockService.getNewMetacode();

        when(metacodeRepository.save(any())).thenReturn(expectedMetacode);

        // Act
        var actualMetacode = tracerMetacodeService.generateMetacode(metacodeDto);

        // Assert
        assertEquals(expectedMetacode.getMetacodeId(), actualMetacode.getMetacodeId());
    }

    /**
     * Test the qualification of Inhibitor metacode generation service for non-Tracer metacode dto.
     */
    @Test
    void testTracerMetacodeGenerationForQualificationOfTracerMetacodeDto() {
        // Arrange
        var expectedValue = true;
        var metacodeDto = mockService.getTracerMetacodeDto();

        // Act
        var isQualified = tracerMetacodeService.isQualified(metacodeDto);

        // Assert
        assertEquals(expectedValue, isQualified);
    }

    /**
     * Test the qualification of Inhibitor metacode generation service for non-Tracer metacode dto.
     */
    @Test
    void testTracerMetacodeGenerationForQualificationOfNonTracerMetacodeDto() {
        // Arrange
        var expectedValue = false;
        var metacodeDto = mockService.getDelMetacodeDto();

        // Act
        var isQualified = tracerMetacodeService.isQualified(metacodeDto);

        // Assert
        assertEquals(expectedValue, isQualified);
    }
}
