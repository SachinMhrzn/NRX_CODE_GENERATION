package delphi.backend.nrxcodegeneration.service;

import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.Metacode;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Singleton
public class DefaultMetacodeService extends AbstractMetacodeGenerator {

    /**
     * Maps a MetacodeDto to as required Metacode.
     *
     * @param metacodeDto The DTO that is to be mapped to a Metacode object.
     * @return The metacode object.
     */
    private Metacode mapMetacodeDtoToMetacode(MetacodeDto metacodeDto) {
        return Metacode
                .builder()
                .programId(metacodeDto.getProgramId())
                .conceptId(metacodeDto.getConceptId())
                .variantId(metacodeDto.getVariantId())
                .seriesId(metacodeDto.getSeriesId())
                .contractId(metacodeDto.getContractId())
                .delLineage(metacodeDto.getDelLineage())
                .targetOneId(metacodeDto.getTargetOneId())
                .targetTwoId(metacodeDto.getTargetTwoId())
                .build();
    }

    /**
     * * Generate a new metacode object from the metacodeDto object.
     * * If the metacode already exists in the database, return the existing metacode.
     * * Otherwise, save the new metacode to the database and return it
     *
     * @param metacodeDto The metacodeDto that we want to save.
     * @return The metacodeDto with the metaCode set to the id of the saved metacode.
     */
    @Override
    public Metacode generateMetacode(MetacodeDto metacodeDto) {
        var nrxMetacode = mapMetacodeDtoToMetacode(metacodeDto);

        return super.getMetacode(nrxMetacode);
    }

    /**
     * Validates if the metacodeDto is qualifies for Default metacode service.
     *
     * @param metacodeDto The metacodeDto that is being evaluated.
     * @return Boolean.
     */
    @Override
    public Boolean isQualified(MetacodeDto metacodeDto) {
        return true;
    }

    /**
     * Get the priority of the service.
     *
     * @return The priority of the service.
     */
    @Override
    public Integer getPriority() {
        return NrxCodeConstant.DEFAULT_METACODE_PRIORITY;
    }
}
