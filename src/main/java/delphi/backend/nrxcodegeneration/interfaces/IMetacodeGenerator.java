package delphi.backend.nrxcodegeneration.interfaces;

import delphi.backend.nrxcodegeneration.model.Metacode;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;

public interface IMetacodeGenerator {

    /**
     * Generate a metacode from a metacodeDto
     *
     * @param metacodeDto The MetacodeDto object that contains the information about the metadata to be generated.
     * @return The generated metacode object.
     */
    Metacode generateMetacode(MetacodeDto metacodeDto);

    /**
     * This function determines if the given metacodeDto is a valid for a metacode generator service.
     *
     * @param metacodeDto The metacodeDto object that is being checked.
     * @return A boolean value.
     */
    Boolean isQualified(MetacodeDto metacodeDto);

    /**
     * Get the priority of the service.
     *
     * @return The priority of the service.
     */
    Integer getPriority();
}
