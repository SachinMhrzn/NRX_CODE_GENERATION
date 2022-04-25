package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import delphi.backend.nrxcodegeneration.model.Concept;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ConceptRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class ConceptService {

    @Inject
    private ConceptRepository conceptRepository;

    /**
     * Given a list of metacode DTOs, return a list of NrxConcepts that are associated with the metacode.
     *
     * @param metacodeDtoList A list of MetacodeDto objects.
     * @return A list of Concepts.
     */
    public List<Concept> getConceptsForMetacodeList(List<MetacodeDto> metacodeDtoList) {
        var concepts = metacodeDtoList.stream()
                .map(MetacodeDto::getConcept)
                .collect(Collectors.toList());

        return concepts.isEmpty()
                ? new ArrayList<>()
                : conceptRepository.findByConceptIn(concepts);
    }

    /**
     * Find the NrxConcept by concept and throw an error if not found
     *
     * @param concepts    The list of concepts.
     * @param conceptName The concept name to be searched for.
     * @return The NrxConcept object.
     */
    public Concept findConceptByConceptNameThrowErrorIfNotFound(List<Concept> concepts, String conceptName) {
        return concepts.stream()
                .filter(concept -> concept.getConcept().equals(conceptName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Concept")));
    }
}
