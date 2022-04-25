package delphi.v1.nrxcodegeneration.service;

import delphi.backend.nrxcodegeneration.service.DefaultMetacodeService;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;

import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.repository.MetacodeRepository;
import delphi.backend.nrxcodegeneration.repository.AbstractMetacodeRepository;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class DefaultMetacodeServiceTest {

    private final MetacodeMockDataService mockService = new MetacodeMockDataService();

    @Mock
    private MetacodeRepository metacodeRepository;

    @Mock
    private AbstractMetacodeRepository abstractMetacodeRepository;

    @InjectMocks
    private DefaultMetacodeService defaultMetacodeService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test default metacode generation for valid meta data.
     */
    @Test
    void testDefaultMetacodeGenerationForValidMetadata() {
        // Arrange
        var metacodeDto = mockService.getDefaultMetacodeDto();
        var expectedMetacode = mockService.getNewMetacode();

        when(metacodeRepository.save(any())).thenReturn(expectedMetacode);

        //Act
        var actualMetacode = defaultMetacodeService.generateMetacode(metacodeDto);

        // Assert
        assertEquals(expectedMetacode.getMetacodeId(), actualMetacode.getMetacodeId());
    }

    /**
     * Test the qualification of Default metacode generation service for default metacode dto.
     */
    @Test
    void testDefaultMetacodeGenerationForQualificationOfDefaultMetacodeDto() {
        // Arrange
        var expectedValue = true;
        var metacodeDto = mockService.getDefaultMetacodeDto();

        //Act
        var isQualified = defaultMetacodeService.isQualified(metacodeDto);

        // Assert
        assertEquals(expectedValue, isQualified);
    }
}
