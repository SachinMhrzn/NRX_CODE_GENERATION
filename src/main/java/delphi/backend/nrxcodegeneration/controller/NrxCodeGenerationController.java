package delphi.backend.nrxcodegeneration.controller;

import java.util.List;
import javax.inject.Inject;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

import delphi.backend.nrxcodegeneration.model.MetacodeDto;
import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.service.ProjectCodeService;
import delphi.backend.nrxcodegeneration.service.MetaCodeGenerationService;
import delphi.backend.nrxcodegeneration.foundation.commoninterface.Validatable;

@Tag(name = "Nrx Code Generation")
@SecurityRequirement(name = NrxCodeConstant.SECURITY_REQUIREMENT_NAME)
@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/v1/codes")
public class NrxCodeGenerationController {

    @Inject
    private ProjectCodeService projectCodeService;

    @Inject
    private MetaCodeGenerationService metaCodeGenerationService;

    /**
     * Generates a project code for the given projectCodeDto.
     *
     * @param projectCodeDTO The ProjectCodeDto object that contains the information needed to generate a project code.
     * @return The generated project code.
     */
    @Validatable
    @Post("/project-code")
    public HttpResponse generateProjectCode(@Body ProjectCodeDto projectCodeDTO) {
        return HttpResponse.ok().body(projectCodeService.generateProjectCode(projectCodeDTO));
    }

    /**
     * Generates project codes for the given list of projectCodeDto.
     *
     * @param projectCodeDtoList The List of ProjectCodeDto object that contains the information needed to generate a project code.
     * @return The generated project code in list.
     */
    @Validatable
    @Post("/project-codes")
    public HttpResponse generateProjectCode(@Body List<ProjectCodeDto> projectCodeDtoList) {
        return HttpResponse.ok().body(projectCodeService.generateProjectCodesInBulk(projectCodeDtoList));
    }

    /**
     * For a given list of metacodeDto, generate metacode for each metacodeDto or add a message if it cannot be generated.
     *
     * @param metacodeDtoList List of metacode dto for which metacode is to be generated.
     * @return The list of generated Metacode.
     */
    @Validatable
    @Post("/meta-codes")
    public HttpResponse generateMetacodes(@Body List<MetacodeDto> metacodeDtoList) {
        return HttpResponse.ok().body(metaCodeGenerationService.generateMetacodes(metacodeDtoList));
    }
}
