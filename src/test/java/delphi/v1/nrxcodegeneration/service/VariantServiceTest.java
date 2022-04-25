package delphi.v1.nrxcodegeneration.service;

import java.util.List;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import delphi.backend.nrxcodegeneration.service.VariantService;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.VariantRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VariantServiceTest {

    private MetacodeMockDataService mockService = new MetacodeMockDataService();

    @InjectMocks
    private VariantService variantService;

    @Mock
    private VariantRepository variantRepository;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test for a valid and existing variant name.
     */
    @Test
    void testVariantForValidAndExistingVariantName() {
        // Arrange
        var variantName = "intermediate";
        var variants = mockService.getAllVariants();
        var variantNames = List.of(variantName);

        when(variantRepository.findByVariantIn(variantNames)).thenReturn(variants);

        //Act
        var actualVariant = variantService.findVariantByVariantNameThrowErrorIfNotFound(variants, variantName);

        // Assert
        assertEquals(variantName, actualVariant.getVariant());
    }

    /**
     * Test for a valid and existing variant name.
     */
    @Test
    void testNrxVariantForInvalidAndNonExistingVariantName() {
        // Arrange
        var variantName = "non-existing-variant-name";
        var variants = mockService.getAllVariants();
        var variantNames = List.of(variantName);

        when(variantRepository.findByVariantIn(variantNames)).thenReturn(variants);

        // Act
        Throwable exception = assertThrows(BadRequestException.class,
                () -> variantService.findVariantByVariantNameThrowErrorIfNotFound(variants, variantName));

        // Assert
        assertEquals(String.format(NrxCodeConstant.NOT_FOUND, "Variant"), exception.getMessage());
    }
}
