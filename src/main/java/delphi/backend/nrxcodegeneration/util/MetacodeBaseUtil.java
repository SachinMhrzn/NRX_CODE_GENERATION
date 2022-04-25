package delphi.backend.nrxcodegeneration.util;

import delphi.backend.nrxcodegeneration.model.MetacodeDto;

public class MetacodeBaseUtil {
    private static final String CTM = "CTM";
    private static final String ON_DNA = "onDNA";
    private static final String TRACER = "Tracer";
    private static final String CLEAVED = "cleaved";
    private static final String DEL_LIGAND = "DEL ligand";

    /**
     * Checks the metacode if it validates for a DEL metacode generator service.
     *
     * @param metacodeDto the metacodeDto object that is being checked
     * @return The Boolean value of whether the metacode is valid for a DEL metacode service.
     */
    public static Boolean isDelMetacodeDto(MetacodeDto metacodeDto) {
        return metacodeDto.getConcept().equals(DEL_LIGAND);
    }

    /**
     * Checks the metacode if it validates for a CTM metacode generator service.
     *
     * @param metacodeDto The metacodeDto that is being checked.
     * @return The Boolean value of whether the metacode is valid for a CTM metacode service.
     */
    public static Boolean isCtmMetacodeDto(MetacodeDto metacodeDto) {
        return metacodeDto.getVariant().equals(CTM) && metacodeDto.getTargetTwo() != null;
    }

    /**
     * Checks the metacode if it validates for a Tracer metacode generator service.
     *
     * @param metacodeDto the metacodeDto object that is being checked
     * @return The boolean value of whether the metacode is a tracer metacode service.
     */
    public static Boolean isTracerMetacodeDto(MetacodeDto metacodeDto) {
        return metacodeDto.getVariant().equals(ON_DNA)
                && metacodeDto.getSeries() != null && metacodeDto.getSeries().equals(TRACER);
    }

    /**
     * Checks the metacode if it validates for Inhibitor metacode generator service.
     *
     * @param metacodeDto the metacodeDto object that is being checked
     * @return The Boolean value of whether the metacode is valid for Inhibitor metacode service.
     */
    public static Boolean isInhibitorMetacodeDto(MetacodeDto metacodeDto) {
        return !isCtmMetacodeDto(metacodeDto) && !isDelMetacodeDto(metacodeDto) && !isTracerMetacodeDto(metacodeDto);
    }
}
