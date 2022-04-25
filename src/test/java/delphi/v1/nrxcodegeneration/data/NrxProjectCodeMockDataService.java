package delphi.v1.nrxcodegeneration.data;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.io.IOException;
import java.util.stream.Collectors;

import io.micronaut.http.server.exceptions.InternalServerException;

import com.fasterxml.jackson.core.type.TypeReference;

import delphi.backend.nrxcodegeneration.model.*;
import delphi.v1.nrxcodegeneration.constant.UnitTestConstants;
import delphi.backend.nrxcodegeneration.enums.ProjectCodeType;
import delphi.backend.nrxcodegeneration.util.ObjectMapperFactory;

public class NrxProjectCodeMockDataService {

    private static final String NRX_USERS_JSON_FILE = "src/test/resources/nrxcodegeneration/nrx-users.json";
    private static final String NRX_TARGETS_JSON_FILE = "src/test/resources/nrxcodegeneration/nrx-targets.json";
    private static final String NRX_PROJECTS_JSON_FILE = "src/test/resources/nrxcodegeneration/nrx-projects.json";
    private static final String PROJECT_CODES_JSON_FILE = "src/test/resources/nrxcodegeneration/project-codes.json";
    private static final String NEW_PROJECT_CODES_JSON_FILE = "src/test/resources/nrxcodegeneration/new-project-codes.json";
    private static final String PROJECT_CODE_DTO_FILE = "src/test/resources/nrxcodegeneration/project-code-dtos.json";

    /**
     * Get a list of all the nrx users.
     *
     * @return A List of NrxUser objects.
     */
    public List<NrxUser> getAllNrxUsers() {
        TypeReference<List<NrxUser>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, NRX_USERS_JSON_FILE);
    }

    /**
     * Get a list of NrxProject objects
     *
     * @return A List of NrxProject objects.
     */
    public List<NrxProject> getAllNrxProjects() {
        TypeReference<List<NrxProject>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, NRX_PROJECTS_JSON_FILE);
    }

    /**
     * Get a list of all Nrx Targets
     *
     * @return A list of NrxTarget objects.
     */
    public List<NrxTarget> getAllNrxTargets() {
        TypeReference<List<NrxTarget>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, NRX_TARGETS_JSON_FILE);
    }

    /**
     * Get a list of all ProjectCodeDto objects Map from json.
     *
     * @return Map of projectCodeDto object <Map<String, ProjectCodeDto>>
     */
    public Map<String, ProjectCodeDto> getAllProjectCodeDtoMap() {
        TypeReference<Map<String, ProjectCodeDto>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, PROJECT_CODE_DTO_FILE);
    }

    /**
     * Get a list of all the ProjectCodeDto objects from json.
     *
     * @return A list of ProjectCodeDto objects.
     */
    public List<ProjectCodeDto> getAllProjectCodeDtoList() {
        return getAllProjectCodeDtoMap().values().stream().collect(Collectors.toList());
    }

    /**
     * Get a list of valid the ProjectCodeDto objects from json.
     *
     * @return A list of ProjectCodeDto objects.
     */
    public List<ProjectCodeDto> getAllValidProjectCodeDtoList() {
        var validProjectCodeDtoList = new ArrayList<ProjectCodeDto>();

        validProjectCodeDtoList.add(getValidProjectCodeDto());
        validProjectCodeDtoList.add(getValidDelAppendedProjectCodeDto());

        return validProjectCodeDtoList;
    }

    /**
     * Get a list of invalid the ProjectCodeDto objects from json.
     *
     * @return A list of ProjectCodeDto objects.
     */
    public List<ProjectCodeDto> getAllInvalidProjectCodeDtoList() {
        var invalidProjectCodeDtoList = new ArrayList<ProjectCodeDto>();

        invalidProjectCodeDtoList.add(getProjectCodeDtoWithInvalidUsername());
        invalidProjectCodeDtoList.add(getProjectCodeDtoWithInvalidProjectName());
        invalidProjectCodeDtoList.add(getProjectCodeDtoWithInvalidTargetName());

        return invalidProjectCodeDtoList;
    }

    /**
     * Get a valid project code dto object
     *
     * @return ProjectCode dto object
     */
    public ProjectCodeDto getValidProjectCodeDto() {
        return getAllProjectCodeDtoMap().get(UnitTestConstants.VALID_PROJECT_CODE_DTO);
    }

    /**
     * Get a valid project code dto object with of type "DEL_APPENDED"
     *
     * @return ProjectCode dto object
     */
    public ProjectCodeDto getValidDelAppendedProjectCodeDto() {
        return getAllProjectCodeDtoMap().get(UnitTestConstants.VALID_DEL_TYPE_PROJECT_CODE_DTO);
    }

    /**
     * Get an invalid project code dto object with invalid username
     *
     * @return ProjectCode dto object
     */
    public ProjectCodeDto getProjectCodeDtoWithInvalidUsername() {
        return getAllProjectCodeDtoMap().get(UnitTestConstants.INVALID_USERNAME_PROJECT_CODE_DTO);
    }

    /**
     * Get an invalid project code dto object with invalid project-name
     *
     * @return ProjectCode dto object
     */
    public ProjectCodeDto getProjectCodeDtoWithInvalidProjectName() {
        return getAllProjectCodeDtoMap().get(UnitTestConstants.INVALID_PROJECT_NAME_PROJECT_CODE_DTO);
    }

    /**
     * Get an invalid project code dto object with invalid target-name
     *
     * @return ProjectCode dto object
     */
    public ProjectCodeDto getProjectCodeDtoWithInvalidTargetName() {
        return getAllProjectCodeDtoMap().get(UnitTestConstants.INVALID_TARGET_NAME_PROJECT_CODE_DTO);
    }

    /**
     * Get a list of ProjectCodeEntity objects
     *
     * @return A List of ProjectCodeEntity objects.
     */
    public List<ProjectCodeEntity> getAllProjectCodeEntities() {
        TypeReference<List<ProjectCodeEntity>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, PROJECT_CODES_JSON_FILE);
    }

    /**
     * Get a list of new ProjectCodeEntity objects
     *
     * @return A List of ProjectCodeEntity objects.
     */
    public List<ProjectCodeEntity> getNewProjectCodeEntities() {
        TypeReference<List<ProjectCodeEntity>> TYPE_REFERENCE = new TypeReference<>() {
        };
        return parseJsonFile(TYPE_REFERENCE, NEW_PROJECT_CODES_JSON_FILE);
    }

    /**
     * Get the user from the mock database and filter them by username
     *
     * @param username The username of the user you want to find.
     * @return The first user that matches the username.
     */
    public NrxUser getNrxUserByUsername(String username) {
        var nrxUsers = getAllNrxUsers();

        return nrxUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the project from the mock database and filter them by project-name
     *
     * @param name The name of the project to retrieve.
     * @return The first NrxProject that matches the name.
     */
    public NrxProject getNrxProjectByName(String name) {
        var nrxProjects = getAllNrxProjects();

        return nrxProjects.stream()
                .filter(project -> project.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get the NrxTarget object from the mock database and filter them by target-name
     *
     * @param name The name of the target to be retrieved.
     * @return The first target that matches the name.
     */
    public NrxTarget getNrxTargetByName(String name) {
        var nrxTargets = getAllNrxTargets();

        return nrxTargets.stream()
                .filter(target -> target.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get ProjectCodeDto object with the valid user, target and project.
     *
     * @return A ProjectCodeDto object.
     */
    public ProjectCodeDto getProjectCodeDtoForValidUserTargetAndProject() {
        return ProjectCodeDto.builder()
                .username("John")
                .projectName("BTK")
                .targetName("BTK")
                .userId(1L)
                .projectId(1L)
                .targetId(1L)
                .build();
    }

    /**
     * Get ProjectCodeDto object with the valid user, target, project and userPCode.
     *
     * @return A ProjectCodeDto object.
     */
    public ProjectCodeDto getProjectCodeDtoForValidUserTargetAndProjectWithUserPCode() {
        return ProjectCodeDto.builder()
                .username("Lucy")
                .projectName("PLATFORM")
                .targetName("Mpro")
                .userPCode("ABC0003")
                .userId(3L)
                .projectId(3L)
                .targetId(3L)
                .build();
    }

    /**
     * Get ProjectCodeDto object with the valid user, target, project which generated new project code.
     *
     * @return A ProjectCodeDto object.
     */
    public ProjectCodeDto getProjectCodeDtoForNewProjectCode() {
        return ProjectCodeDto.builder()
                .username("Sam")
                .projectName("BTK")
                .targetName("Mpro")
                .build();
    }

    /**
     * Get ProjectCodeDto object with the valid user, target, project and userPCode which generated new project code.
     *
     * @return A ProjectCodeDto object.
     */
    public ProjectCodeDto getProjectCodeDtoForNewProjectCodeWithUserPCode() {
        return ProjectCodeDto.builder()
                .username("Sam")
                .projectName("BRAF")
                .targetName("Mpro")
                .userPCode("ABC09")
                .userId(5L)
                .projectId(2L)
                .targetId(3L)
                .build();
    }

    /**
     * Get ProjectCodeDto for valid user, target and project with projectCodeType as DEL_APPENDED
     *
     * @return The ProjectCodeDto object
     */
    public ProjectCodeDto getDELProjectCodeDtoForValidUserTargetAndProject() {
        return ProjectCodeDto.builder()
                .username("Sam")
                .projectName("COVID-19")
                .targetName("No Target")
                .userId(5L)
                .projectId(5L)
                .targetId(5L)
                .projectCodeType(ProjectCodeType.DEL_APPENDED)
                .build();
    }

    /**
     * Get ProjectCodeDto for valid user, target and project with projectCodeType as DEL_APPENDED for new DEL project code generation.
     *
     * @return The ProjectCodeDto object
     */
    public ProjectCodeDto getDELProjectCodeDtoForNewDELProjectCode() {
        return ProjectCodeDto.builder()
                .username("Emmey")
                .projectName("BRAF")
                .targetName("Aurora A")
                .userId(2L)
                .projectId(2L)
                .targetId(2L)
                .projectCodeType(ProjectCodeType.DEL_APPENDED)
                .build();
    }

    /**
     * Parses json file and converts it into specified type.
     *
     * @param typeReference The TypeReference that will be used to deserialize the JSON file.
     * @param name          The name of the file to be parsed.
     * @return Parsed Object from json.
     */
    private <T> T parseJsonFile(TypeReference<T> typeReference, String name) {
        try {
            return ObjectMapperFactory.getInstance().readValue(
                    new File(name), typeReference
            );
        } catch (IOException ioException) {
            throw new InternalServerException(ioException.getMessage());
        }
    }

    /**
     * Find a ProjectCodeEntity by its ID
     *
     * @param projectCode The project code to search for.
     * @return Nothing.
     */
    public Optional<ProjectCodeEntity> findProjectEntityById(ProjectCodeEntity projectCode) {
        var projectEntities = getAllProjectCodeEntities();
        projectEntities.addAll(getNewProjectCodeEntities());

        return projectEntities
                .stream()
                .filter(projectCodeEntity -> projectCodeEntity.getProjectCodeID().equals(projectCode.getProjectCodeID()))
                .findFirst();
    }

    /**
     * Find project codes that match the given user, project, and target
     *
     * @param nrxUser    The user to filter by.
     * @param nrxProject The project to filter by.
     * @param nrxTarget  The target that the user is working on.
     * @return A ProjectCodeEntity objects.
     */
    public ProjectCodeEntity findProjectEntityByUserTargetAndProject(NrxUser nrxUser, NrxProject nrxProject, NrxTarget nrxTarget) {
        var projectCodeEntities = getAllProjectCodeEntities();

        return projectCodeEntities.stream()
                .filter(projectCode -> projectCode.getPersonPId().equals(nrxUser.getId())
                        && projectCode.getProjectPId().equals(nrxProject.getId())
                        && projectCode.getTargetId().equals(nrxTarget.getId())
                ).findFirst().orElse(null);
    }

    /**
     * It maps the ProjectCodeEntity to ProjectCodeDto.
     *
     * @param projectCode The ProjectCodeEntity object that is being mapped.
     * @return The ProjectCodeDto object.
     */
    public ProjectCodeDto mapProjectCodeDto(ProjectCodeEntity projectCode) {
        return ProjectCodeDto.builder()
                .projectCode(projectCode.getProjectCode())
                .projectCodeType(null)
                .build();
    }

    /**
     * Get the first ProjectCodeEntity in the list of ProjectCodeEntities.
     *
     * @return The first ProjectCodeEntity in the list of ProjectCodeEntities.
     */
    public ProjectCodeEntity saveAndGetNewProjectCodeEntity() {
        var projectCodeEntities = getNewProjectCodeEntities();

        return projectCodeEntities.get(0);
    }

    /**
     * Find the NrxUsers that have the same username as the given username
     *
     * @param username The username of the user to find.
     * @return A list of NrxUser objects.
     */
    public List<NrxUser> findNrxUsersByUsername(String username) {
        var nrxUsers = getAllNrxUsers();

        return nrxUsers.stream().filter(nrxUser -> nrxUser.getUsername().equals(username)).collect(Collectors.toList());
    }

    /**
     * Find the NrxTargets that have the same name as the targetName parameter
     *
     * @param targetName The name of the target to find.
     * @return A list of NrxTarget objects.
     */
    public List<NrxTarget> findNrxTargetByTargetName(String targetName) {
        var nrxTargets = getAllNrxTargets();

        return nrxTargets.stream().filter(nrxTarget -> nrxTarget.getName().equals(targetName)).collect(Collectors.toList());
    }

    /**
     * Find all NRX projects that have the same name as the given project name
     *
     * @param projectName The name of the project to search for.
     * @return A list of NrxProject objects.
     */
    public List<NrxProject> findNrxProjectsByProjectName(String projectName) {
        var nrxProjects = getAllNrxProjects();

        return nrxProjects.stream().filter(nrxProject -> nrxProject.getName().equals(projectName)).collect(Collectors.toList());
    }

    /**
     * Get the first ProjectCodeEntity in the list of ProjectCodeEntities.
     *
     * @return The first ProjectCodeEntity in the list of ProjectCodeEntities.
     */
    public ProjectCodeEntity saveAndGetNewDELProjectCodeEntity() {
        var projectCodeEntities = getNewProjectCodeEntities();

        return projectCodeEntities.get(1);
    }

    /**
     * This function returns the first ProjectCodeEntity in the list of ProjectCodeEntities returned by the
     * getNewProjectCodeEntities function
     *
     * @return The first ProjectCodeEntity in the list of new ProjectCodeEntities.
     */
    public ProjectCodeEntity fetchExistingDelAppendedProjectCode(Long userId, Long projectId, Long targetId) {
        return getAllProjectCodeEntities()
                .stream()
                .filter(projectCode -> projectCode.getPersonPId().equals(userId)
                        && projectCode.getProjectPId().equals(projectId)
                        && projectCode.getTargetId().equals(targetId)
                        && projectCode.getProjectCode().startsWith("DEL")
                ).findFirst()
                .orElse(null);
    }
}
