package delphi.backend.nrxcodegeneration.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

import io.micronaut.core.annotation.Introspected;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import delphi.backend.nrxcodegeneration.enums.ProjectCodeType;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class ProjectCodeDto {

    /**
     * Username for the user whose project-code is to be generated.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_USERNAME)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_USERNAME)
    private String username;

    /**
     * Project name for the project whose project-code is to be generated.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_PROJECT_NAME)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_PROJECT_NAME)
    private String projectName;

    /**
     * Target name for the target of a project whose project-code is to be generated.
     */
    @NotNull(message = NrxCodeConstant.REQUIRED_TARGET_NAME)
    @NotEmpty(message = NrxCodeConstant.NON_EMPTY_TARGET_NAME)
    private String targetName;

    /**
     * User generated project-code.
     */
    private String userPCode;

    /**
     * Generated project-code.
     */
    private String projectCode;

    /**
     * Enum type to distinguish if the required project code is normal or "DEL" appended.
     */
    @Builder.Default
    private ProjectCodeType projectCodeType = ProjectCodeType.NORMAL;

    /**
     * ID to map the project-code. Required when project-code is generated in bulk.
     */
    private Long id;

    /**
     * Hold the message if project-code generation is unsuccessful.
     */
    private String message;

    /**
     * ID for Project.
     */
    @JsonIgnore
    private Long projectId;

    /**
     * ID for User.
     */
    @JsonIgnore
    private Long userId;

    /**
     * ID for Target.
     */
    @JsonIgnore
    private Long targetId;
}
