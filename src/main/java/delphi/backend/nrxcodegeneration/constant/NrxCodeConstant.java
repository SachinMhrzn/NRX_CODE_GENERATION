package delphi.backend.nrxcodegeneration.constant;

import java.util.Map;

public class NrxCodeConstant {
    public static final String SECURITY_REQUIREMENT_NAME = "BearerAuth";

    // Database Schema
    public static final String CHEMREG_SCHEMA = "CHEMREG";

    // Database Constants
    public static final String PROJECT_TABLE = "PROJECT";
    public static final String PERSONNEL_TABLE = "PERSONNEL";
    public static final String NRX_SERIES_TABLE = "NRX_SERIES";
    public static final String NRX_TARGETS_TABLE = "NRX_TARGETS";
    public static final String NRX_CONCEPT_TABLE = "NRX_CONCEPT";
    public static final String NRX_PROGRAM_TABLE = "NRX_PROGRAM";
    public static final String NRX_VARIANT_TABLE = "NRX_VARIANT";
    public static final String NRX_METACODE_TABLE = "NRX_METACODE";
    public static final String NRX_CONTRACT_TABLE = "NRX_CONTRACT";
    public static final String NRX_USERPCODE_TABLE = "NRX_USERPCODE";
    public static final String NRX_PROJECTCODE_TABLE = "NRX_PROJECTCODE";
    public static final String NRX_DEL_LIBRARY_TABLE = "NRX_DEL_LIBRARY";

    public static final String CHEMREG_DATASOURCE = "CHEMREG";

    // Message Constants
    public static final String NOT_FOUND = "%s not found or does not exist. ";

    public static final String REQUIRED_PROGRAM = "Program is a required field";
    public static final String REQUIRED_CONCEPT = "Concept is a required field";
    public static final String REQUIRED_VARIANT = "Variant is a required field";
    public static final String REQUIRED_USERNAME = "username is a required field";
    public static final String REQUIRED_CONTRACT = "Contract is a required field";
    public static final String REQUIRED_TARGET_ONE = "Target One is a required field";
    public static final String REQUIRED_TARGET_NAME = "targetName is a required field";
    public static final String REQUIRED_PROJECT_NAME = "projectName is a required field";

    public static final String NON_EMPTY_SERIES = "Series cannot be empty";
    public static final String NON_EMPTY_PROGRAM = "Program cannot be empty";
    public static final String NON_EMPTY_CONCEPT = "Concept cannot be empty";
    public static final String NON_EMPTY_VARIANT = "Variant cannot be empty";
    public static final String NON_EMPTY_USERNAME = "username cannot be empty";
    public static final String NON_EMPTY_CONTRACT = "Contract cannot be empty";
    public static final String NON_EMPTY_TARGET_ONE = "Target One cannot be empty";
    public static final String NON_EMPTY_TARGET_TWO = "Target Two cannot be empty";
    public static final String NON_EMPTY_TARGET_NAME = "targetName cannot be empty";
    public static final String NON_EMPTY_DEL_LINEAGE = "Del Lineage cannot be empty";
    public static final String NON_EMPTY_PROJECT_NAME = "projectName cannot be empty";

    public static final String METACODE_SERVICE_NOT_FOUND = "No service found for the given metadata";
    public static final String PROJECT_CODE_SERVICE_NOT_FOUND = "No service found for the given project code data";

    public static final Map<String, String> MAP_ERROR_CODE_MESSAGE = Map.of(
            "ORA-20000", "User not found while creating project code",
            "ORA-20001", "Active user required to create project code",
            "ORA-20002", "Project not found while creating project code",
            "ORA-20003", "Active project required to create project code",
            "ORA-20004", "Active project code already exists for provided project"
    );

    // Service Constants
    public static final String DEL_PREFIX = "DEL";
    public static final String PROJECT_CODE_ACTIVE_STATUS = "yes";
    public static final String STARTING_DEL_PROJECT_CODE = "DEL0001";

    public static final Integer PROJECT_CODE_MIN_NUMBER = 4;
    public static final Integer PROJECT_CODE_INCREMENT_FACTOR = 1;

    // Metadata service priority
    public static final Integer DEFAULT_METACODE_PRIORITY = 0;
    public static final Integer INHIBITOR_METACODE_PRIORITY = 1;
    public static final Integer DEL_METACODE_PRIORITY = 2;
    public static final Integer CTM_METACODE_PRIORITY = 3;
    public static final Integer TRACER_METACODE_PRIORITY = 4;
}
