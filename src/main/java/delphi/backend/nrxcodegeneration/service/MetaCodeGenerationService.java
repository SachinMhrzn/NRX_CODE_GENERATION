package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import delphi.backend.nrxcodegeneration.model.*;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;
import delphi.backend.nrxcodegeneration.foundation.error.exception.MetacodeGenerationException;

@Singleton
public class MetaCodeGenerationService {

    @Inject
    private NrxTargetService nrxTargetService;

    @Inject
    private VariantService variantService;

    @Inject
    private ConceptService conceptService;

    @Inject
    private SeriesService seriesService;

    @Inject
    private ProgramService programService;

    @Inject
    private ContractService contractService;

    @Inject
    private MetacodeServiceProvider metacodeServiceProvider;

    /**
     * This function takes a list of MetacodeDto objects and generates a MetacodeGenerationResponse object
     *
     * @param metacodeDtoList List<MetacodeDto>
     * @return The metacodeGenerationResponse object contains the metacode and the error messages.
     */
    @Transactional
    public MetacodeGenerationResponse generateMetacodes(List<MetacodeDto> metacodeDtoList) {
        validateAndSetMetadataId(metacodeDtoList);

        var invalidMetacodeDtoList = metacodeDtoList
                .stream()
                .filter(metacodeDto -> metacodeDto.getMessage() != null)
                .collect(Collectors.toList());
        metacodeDtoList.removeAll(invalidMetacodeDtoList);

        var validMetacodeDtoList = new ArrayList<MetacodeDto>();

        metacodeDtoList.forEach(metacodeDto -> {
            try {
                var metacodeService = metacodeServiceProvider.find(metacodeDto);
                var metacode = metacodeService.generateMetacode(metacodeDto);

                metacodeDto.setMetaCode(metacode.getMetacodeId());

                validMetacodeDtoList.add(metacodeDto);
            } catch (MetacodeGenerationException | BadRequestException exception) {
                metacodeDto.setMessage(exception.getMessage());

                invalidMetacodeDtoList.add(metacodeDto);
            }
        });

        return MetacodeGenerationResponse.builder()
                .succeeded(validMetacodeDtoList)
                .failed(invalidMetacodeDtoList)
                .error(!invalidMetacodeDtoList.isEmpty())
                .build();
    }

    /**
     * * Validate and set the metadata IDs
     * * Find the corresponding nrx series, variant, program, concept, target one, target two, and contract.
     * * Set the corresponding ids in the metacode data
     *
     * @param metacodeDtoList The list of metacodeDto objects that to validate.
     */
    private void validateAndSetMetadataId(List<MetacodeDto> metacodeDtoList) {
        var targets = nrxTargetService.getNrxTargetsForMetacodeList(metacodeDtoList);
        var seriesList = seriesService.getSeriesForMetacodeList(metacodeDtoList);
        var variants = variantService.getVariantsForMetacodeList(metacodeDtoList);
        var programs = programService.getProgramsForMetacodeList(metacodeDtoList);
        var concepts = conceptService.getConceptsForMetacodeList(metacodeDtoList);
        var contracts = contractService.getContractsForMetacodeList(metacodeDtoList);

        metacodeDtoList.forEach(metacodeDto -> {
            try {
                var series = seriesService.findSeriesBySeriesNameThrowErrorIfInvalid(seriesList, metacodeDto.getSeries());
                var variant = variantService.findVariantByVariantNameThrowErrorIfNotFound(variants, metacodeDto.getVariant());
                var program = programService.findProgramByProgramNameThrowErrorIfNotFound(programs, metacodeDto.getProgram());
                var concept = conceptService.findConceptByConceptNameThrowErrorIfNotFound(concepts, metacodeDto.getConcept());
                var targetTwo = nrxTargetService.findNrxTargetByTargetTwoThrowErrorIfInvalid(targets, metacodeDto.getTargetTwo());
                var targetOne = nrxTargetService.findNrxTargetByTargetOneThrowErrorIfNotFound(targets, metacodeDto.getTargetOne());
                var contract = contractService.findContractByContractNameThrowErrorIfNotFound(contracts, metacodeDto.getContract());

                metacodeDto.setProgramId(program.getId());
                metacodeDto.setVariantId(variant.getId());
                metacodeDto.setConceptId(concept.getId());
                metacodeDto.setContractId(contract.getId());
                metacodeDto.setTargetOneId(targetOne.getId());
                metacodeDto.setSeriesId(series != null ? series.getId() : null);
                metacodeDto.setTargetTwoId(targetTwo != null ? targetTwo.getId() : null);
            } catch (BadRequestException exception) {
                metacodeDto.setMessage(exception.getMessage());
            }
        });
    }
}
