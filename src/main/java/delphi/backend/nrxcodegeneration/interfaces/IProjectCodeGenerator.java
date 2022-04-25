package delphi.backend.nrxcodegeneration.interfaces;

import delphi.backend.nrxcodegeneration.model.ProjectCodeDto;
import delphi.backend.nrxcodegeneration.enums.ProjectCodeType;

public interface IProjectCodeGenerator {

    /**
     * Generate a project code
     *
     * @param projectCodeDto The ProjectCodeDto object that will be used to generate the project code.
     * @return ProjectCodeDto
     */
    ProjectCodeDto generate(ProjectCodeDto projectCodeDto);

    /**
     * Returns true if the project code type is qualified
     *
     * @param projectCodeType The project code type to check.
     * @return A boolean value.
     */
    Boolean isQualified(ProjectCodeType projectCodeType);
}
