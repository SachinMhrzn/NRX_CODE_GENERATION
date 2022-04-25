package delphi.v1.nrxcodegeneration.service;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.MockitoJUnitRunner;

import delphi.backend.nrxcodegeneration.service.*;
import delphi.v1.nrxcodegeneration.data.MetacodeMockDataService;
import delphi.backend.nrxcodegeneration.model.MetacodeGenerationResponse;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MetacodeServiceTest {

    private final MetacodeMockDataService mockService = new MetacodeMockDataService();

    @Mock
    private NrxTargetService nrxTargetService;

    @Mock
    private VariantService variantService;

    @Mock
    private ConceptService conceptService;

    @Mock
    private SeriesService seriesService;

    @Mock
    private ProgramService programService;

    @Mock
    private ContractService contractService;

    @Mock
    private MetacodeServiceProvider metacodeServiceProvider;

    @Mock
    private DelMetacodeService delMetacodeService;

    @InjectMocks
    private MetaCodeGenerationService metaCodeGenerationService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    /**
     * Test metacode generation for valid list of meta data.
     */
    @Test
    void testMetacodeGenerationForValidListOfMetadata() {
        // Arrange
        var metacodeDtoList = mockService.getAllMetacodeDto();
        var metacodeDto = metacodeDtoList.get(0);
        var newMetacode = mockService.getNewMetacode();

        var seriesList = mockService.getAllSeries();
        var variants = mockService.getAllVariants();
        var programs = mockService.getAllPrograms();
        var concepts = mockService.getAllConcepts();
        var targets = mockService.getAllNrxTargets();
        var contracts = mockService.getAllContracts();

        var series = mockService.findSeriesByName(metacodeDto.getSeries());
        var variant = mockService.findVariantByName(metacodeDto.getVariant());
        var program = mockService.findProgramByName(metacodeDto.getProgram());
        var concept = mockService.findConceptByName(metacodeDto.getConcept());
        var contract = mockService.findContractByName(metacodeDto.getContract());
        var targetTwo = mockService.findNrxTargetByName(metacodeDto.getTargetTwo());
        var targetOne = mockService.findNrxTargetByName(metacodeDto.getTargetOne());

        // Expected Value
        var expectedErrorFlag = false;
        var expectedSizeOfSucceededGeneration = mockService.getAllMetacodeDto().size();
        var expectedSizeOfFailedGeneration = 0;

        // Mock Services
        when(seriesService.getSeriesForMetacodeList(anyList())).thenReturn(seriesList);
        when(variantService.getVariantsForMetacodeList(anyList())).thenReturn(variants);
        when(programService.getProgramsForMetacodeList(anyList())).thenReturn(programs);
        when(conceptService.getConceptsForMetacodeList(anyList())).thenReturn(concepts);
        when(contractService.getContractsForMetacodeList(anyList())).thenReturn(contracts);
        when(nrxTargetService.getNrxTargetsForMetacodeList(anyList())).thenReturn(targets);

        when(seriesService.findSeriesBySeriesNameThrowErrorIfInvalid(anyList(), anyString())).thenReturn(series);
        when(variantService.findVariantByVariantNameThrowErrorIfNotFound(anyList(), anyString())).thenReturn(variant);
        when(programService.findProgramByProgramNameThrowErrorIfNotFound(anyList(), anyString())).thenReturn(program);
        when(conceptService.findConceptByConceptNameThrowErrorIfNotFound(anyList(), anyString())).thenReturn(concept);
        when(nrxTargetService.findNrxTargetByTargetTwoThrowErrorIfInvalid(anyList(), anyString())).thenReturn(targetTwo);
        when(nrxTargetService.findNrxTargetByTargetOneThrowErrorIfNotFound(anyList(), anyString())).thenReturn(targetOne);
        when(contractService.findContractByContractNameThrowErrorIfNotFound(anyList(), anyString())).thenReturn(contract);

        when(metacodeServiceProvider.find(any())).thenReturn(delMetacodeService);
        when(delMetacodeService.generateMetacode(any())).thenReturn(newMetacode);

        // Act
        var actualMetacodeGenerationResponse = metaCodeGenerationService.generateMetacodes(metacodeDtoList);

        // Assert
        assertMetadataResponseEqual(
                expectedErrorFlag,
                expectedSizeOfFailedGeneration,
                expectedSizeOfSucceededGeneration,
                actualMetacodeGenerationResponse
        );
    }

    /**
     * Asserts that the actual metacode generation response is equal to the expected metacode generation response
     *
     * @param expectedErrorFlag                 The boolean flag that indicates whether the generation of metacode was successful or not.
     * @param expectedSizeOfFailedGeneration    The number of metacodes that were expected to fail.
     * @param expectedSizeOfSucceededGeneration The number of metacodes that should have been generated.
     * @param actualMetacodeGenerationResponse  The actual response from the server.
     */
    private void assertMetadataResponseEqual(
            Boolean expectedErrorFlag,
            Integer expectedSizeOfFailedGeneration,
            Integer expectedSizeOfSucceededGeneration,
            MetacodeGenerationResponse actualMetacodeGenerationResponse
    ) {
        assertEquals(expectedErrorFlag, actualMetacodeGenerationResponse.getError());
        assertEquals(expectedSizeOfSucceededGeneration, actualMetacodeGenerationResponse.getSucceeded().size());
        assertEquals(expectedSizeOfFailedGeneration, actualMetacodeGenerationResponse.getFailed().size());
    }
}
