package delphi.backend;

import static io.micronaut.core.util.CollectionUtils.mapOf;

import io.micronaut.context.env.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import io.micronaut.runtime.Micronaut;
import io.micronaut.context.ApplicationContext;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import delphi.backend.constant.RegistrationConstant;
import delphi.backend.service.secrets.DelphiSecretsManager;

@SecurityScheme(name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "jwt")
@OpenAPIDefinition(
        info = @Info(
                title = RegistrationConstant.MODULE_NAME,
                version = "0.0",
                description = "APIs for " + RegistrationConstant.MODULE_NAME))
public class EntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPoint.class);

    /**
     * Application context for Micronaut.
     */
    public static ApplicationContext context;

    public static void main(String[] args) {

        try {
            context = Micronaut.build("delphi")
                    .mainClass(EntryPoint.class)
                    .propertySources(PropertySource.of("delphi", loadEnv()))
                    .start();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());

            throw ex;
        }
    }

    /**
     * Fetch the secrets from secret manager and load the env
     *
     * @return Map
     */
    public static Map<String, Object> loadEnv() {

        DelphiSecretsManager delphiSecretsManager = new DelphiSecretsManager();

        return mapOf(
                "datasources.default.username", delphiSecretsManager.getValue(
                        "DATASOURCES_CR_USERNAME", ""),
                "datasources.default.url", delphiSecretsManager.getValue(
                        "DATASOURCES_DEFAULT_URL", ""),
                "datasources.default.password", delphiSecretsManager.getValue(
                        "DATASOURCES_DEFAULT_PASSWORD", ""),
                "datasources.default.driverClassName", delphiSecretsManager.getValue(
                        "DATASOURCES_DEFAULT_DRIVERCLASSNAME", ""),
                "datasources.default.dialect", delphiSecretsManager.getValue(
                        "DATASOURCES_DEFAULT_DIALECT", ""),
                "datasources.default.schema-generate", delphiSecretsManager.getValue(
                        "DATASOURCES_DEFAULT_SCHEMA_GENERATE", ""),
                "datasources.default.maximum-pool-size", delphiSecretsManager.getValue(
                        "DATASOURCES_MAX_POOL_SIZE", "10"),
                "datasources.default.minimum-idle", delphiSecretsManager.getValue(
                        "DATASOURCES_MINIMUM_IDLE_CONNECTION", "10"),
                "datasources.default.max-lifetime", delphiSecretsManager.getValue(
                        "DATASOURCES_MAX_LIFETIME", "1800000"),
                "datasources.default.idle-timeout", delphiSecretsManager.getValue(
                        "DATASOURCES_IDLE_TIMEOUT", "600000"),


                "micronaut.security.token.jwt.signatures.secret.generator.secret",
                delphiSecretsManager.getValue("JWT_GENERATOR_SECRET", ""),

                "delphi.s3.bucket", delphiSecretsManager.getValue("S3_BUCKET", ""),
                "delphi.s3.base-url", delphiSecretsManager.getValue("S3_BASE_URL", ""),
                "delphi.s3.presigned-expiration-time", delphiSecretsManager.getValue(
                        "PRESIGNED_EXPIRATION_TIME_IN_MINUTES", ""),
                "delphi.thread-count", delphiSecretsManager.getValue("THREAD_COUNT", "20"),

                "delphi.flag.is-development-environment",
                delphiSecretsManager.getValue("IS_DEVELOPMENT_ENVIRONMENT", "TRUE"),

                "delphi.ldap.host-url", delphiSecretsManager.getValue("LDAP_HOST_URL", "ldap://localhost:389"),
                "delphi.ldap.user-identifier", delphiSecretsManager.getValue("LDAP_USER_IDENTIFIER", "dc=delphi,dc=local")
        );

    }
}
