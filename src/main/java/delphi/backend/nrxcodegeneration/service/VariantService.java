package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import delphi.backend.nrxcodegeneration.model.Variant;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.VariantRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class VariantService {

    @Inject
    private VariantRepository variantRepository;

    /**
     * Given a list of metacodeDto objects, return a list of nrxVariant objects
     *
     * @param metacodeDtoList A list of MetacodeDto objects.
     * @return A list of Variant objects.
     */
    public List<Variant> getVariantsForMetacodeList(List<MetacodeDto> metacodeDtoList) {
        var variants = metacodeDtoList.stream()
                .map(MetacodeDto::getVariant)
                .collect(Collectors.toList());

        return variants.isEmpty()
                ? new ArrayList<>()
                : variantRepository.findByVariantIn(variants);
    }

    /**
     * Find the Variant by variant and throw an error if not found
     *
     * @param variants    The list of variants.
     * @param variantName The variant name to find.
     * @return The Variant object.
     */
    public Variant findVariantByVariantNameThrowErrorIfNotFound(List<Variant> variants, String variantName) {
        return variants.stream()
                .filter(variant -> variant.getVariant().equals(variantName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Variant")));
    }
}
