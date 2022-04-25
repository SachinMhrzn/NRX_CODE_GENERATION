package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import java.util.ArrayList;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import delphi.backend.nrxcodegeneration.model.NrxTarget;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.NrxTargetRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class NrxTargetService {

    @Inject
    private NrxTargetRepository nrxTargetRepository;

    /**
     * Find nrx target by name and throw an error if not found.
     *
     * @param targetName The name of the target to find.
     * @return NrxTarget.
     * @throws BadRequestException If the target with given targetName is not found.
     */
    public NrxTarget findNrxTargetByNameThrowErrorIfNotFound(String targetName) {
        return nrxTargetRepository.findByName(targetName)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Target")));
    }

    /**
     * Given a list of metacodeDto objects, return a list of nrxTarget objects
     *
     * @param metacodeDtoList A list of MetacodeDto objects.
     * @return A list of NrxTarget objects.
     */
    public List<NrxTarget> getNrxTargetsForMetacodeList(List<MetacodeDto> metacodeDtoList) {
        var targetOnes = metacodeDtoList.stream()
                .map(MetacodeDto::getTargetOne)
                .collect(Collectors.toList());
        var targetTwos = metacodeDtoList.stream()
                .map(MetacodeDto::getTargetTwo)
                .collect(Collectors.toList());

        List<String> targets = new ArrayList<>(targetOnes);
        targets.addAll(targetTwos);

        return targets.isEmpty()
                ? new ArrayList<>()
                : nrxTargetRepository.findByNameIn(targets);
    }

    /**
     * Find the target by name and return it. If not found, throw an exception
     *
     * @param targets    The list of targets.
     * @param targetName The name of the target to find.
     * @return NrxTarget.
     */
    public NrxTarget findNrxTargetByTargetOneThrowErrorIfNotFound(List<NrxTarget> targets, String targetName) {
        return targets.stream()
                .filter(nrxTarget -> nrxTarget.getName().equals(targetName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Target 1")));
    }

    /**
     * Find the target with the given name in the list of targets
     *
     * @param targets    A list of all the targets.
     * @param targetName The name of the target to find.
     * @return The NrxTarget object.
     */
    public NrxTarget findNrxTargetByTargetTwoThrowErrorIfInvalid(List<NrxTarget> targets, String targetName) {
        if (targetName == null) return null;

        return targets.stream()
                .filter(nrxTarget -> nrxTarget.getName().equals(targetName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Target 2")));
    }

}
