package delphi.backend.nrxcodegeneration.service;

import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.Metacode;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.util.MetacodeBaseUtil;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Singleton
public class TracerMetacodeService extends AbstractMetacodeGenerator {

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
                .contractId(metacodeDto.getContractId())
                .targetOneId(metacodeDto.getTargetOneId())
                .seriesId(metacodeDto.getSeriesId())
                .build();
    }

    /**
     * * Generate a new metacode object from the metacodeDto object.
     * * If the metacode already exists in the database, return the existing metacode.
     * * Otherwise, save the new metacode to the database and return it
     *
     * @param metacodeDto The metacodeDto that we want to save.
     * @return The metacode object with metacode id.
     */
    @Override
    public Metacode generateMetacode(MetacodeDto metacodeDto) {
        var nrxMetacode = mapMetacodeDtoToMetacode(metacodeDto);

        return super.getMetacode(nrxMetacode);
    }

    /**
     * Validates if the metacodeDto is qualifies for Tracer metacode service.
     *
     * @param metacodeDto The metacodeDto that is being evaluated.
     * @return Boolean.
     */
    @Override
    public Boolean isQualified(MetacodeDto metacodeDto) {
        return MetacodeBaseUtil.isTracerMetacodeDto(metacodeDto);
    }

    /**
     * Get the priority of the service.
     *
     * @return The priority of the service.
     */
    @Override
    public Integer getPriority() {
        return NrxCodeConstant.TRACER_METACODE_PRIORITY;
    }
}
