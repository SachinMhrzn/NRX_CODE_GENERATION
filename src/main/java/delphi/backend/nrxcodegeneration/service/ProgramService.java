package delphi.backend.nrxcodegeneration.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

import delphi.backend.nrxcodegeneration.model.Program;
import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.ProgramRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class ProgramService {

    @Inject
    private ProgramRepository programRepository;

    /**
     * Given a list of metacodeDto objects, return a list of nrxProgram objects
     *
     * @param metacodeDtoList A list of MetacodeDto objects.
     * @return A list of Program objects.
     */
    public List<Program> getProgramsForMetacodeList(List<MetacodeDto> metacodeDtoList) {
        var programs = metacodeDtoList.stream()
                .map(MetacodeDto::getProgram)
                .collect(Collectors.toList());

        return programRepository.findByProgramIn(programs);
    }

    /**
     * Find the Program by the program name.
     *
     * @param programs    The list of programs.
     * @param programName The program name to be searched for.
     * @return The Program object.
     */
    public Program findProgramByProgramNameThrowErrorIfNotFound(List<Program> programs, String programName) {
        return programs.stream()
                .filter(program -> program.getProgram().equals(programName))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "Program")));
    }
}
