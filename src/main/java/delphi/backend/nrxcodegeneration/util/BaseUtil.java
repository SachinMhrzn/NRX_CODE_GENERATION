package delphi.backend.nrxcodegeneration.util;

public class BaseUtil {

    /**
     * Checks if the environment is local of not
     *
     * @return boolean
     */
    public static boolean isLocalEnvironment() {
        String environment = System.getenv("DELPHI_ENVIRONMENT");

        return environment != null && environment.equals("LOCAL");
    }
}
