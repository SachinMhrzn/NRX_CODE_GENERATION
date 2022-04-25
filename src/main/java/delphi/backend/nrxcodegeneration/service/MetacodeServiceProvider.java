package delphi.backend.nrxcodegeneration.service;

import java.util.Set;
import javax.inject.Inject;
import java.util.Comparator;
import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.interfaces.IMetacodeGenerator;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class MetacodeServiceProvider {

    private Set<IMetacodeGenerator> metacodeGeneratorSet;

    @Inject
    public MetacodeServiceProvider(Set<IMetacodeGenerator> metacodeGeneratorSet) {
        this.metacodeGeneratorSet = metacodeGeneratorSet;
    }

    /**
     * Find the first service that qualifies the metacodeDto
     *
     * @param metacodeDto The metacodeDto to find a service for.
     * @return The metacode generator that qualifies for the given metacode.
     */
    public IMetacodeGenerator find(MetacodeDto metacodeDto) {
        return metacodeGeneratorSet
                .stream()
                .filter(service -> service.isQualified(metacodeDto))
                .max(Comparator.comparing(IMetacodeGenerator::getPriority))
                .orElseThrow(() -> new BadRequestException(NrxCodeConstant.METACODE_SERVICE_NOT_FOUND));
    }
}
