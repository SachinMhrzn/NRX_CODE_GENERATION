package delphi.backend.service.secrets;

import javax.inject.Singleton;

import com.google.gson.*;
import com.nurixtx.delphi.common.util.GsonFactory;
import com.amazonaws.services.secretsmanager.model.AWSSecretsManagerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cdimascio.dotenv.Dotenv;

import delphi.backend.nrxcodegeneration.util.BaseUtil;

@Singleton
public class DelphiSecretsManager {

    private static final Logger LOG = LoggerFactory.getLogger(DelphiSecretsManager.class.getName());
    private Gson gson = GsonFactory.getInstance();
    private Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public String getValue(String key) {
        String secret = null;
        try {
            var secretString = SecretsManager.getSecret();
            JsonObject secretJsonObject = gson.fromJson(secretString, JsonObject.class);
            // TODO Use getStringFromJsonElement when merged
            JsonElement secretJsonElement = secretJsonObject.get(key);
            if (secretJsonElement != null) {
                secret = secretJsonElement.getAsString();
            }
        } catch (AWSSecretsManagerException exception) {
            LOG.error(exception.getMessage());
        }

        if (secret == null) {
            secret = System.getenv(key);
        }

        if (secret == null) {
            throw new IllegalArgumentException("Secret not found for key:" + key);
        }

        return secret;
    }

    /**
     * Get the value of secret key or use default value for local environment.
     *
     * @param key
     * @param localDefaultValue
     * @return String
     */
    public String getValue(String key, String localDefaultValue) {
        if (BaseUtil.isLocalEnvironment()) {
            String value = System.getenv(key);
            if (value != null) {
                return value;
            }
            return dotenv.get(key, localDefaultValue);
        }
        return this.getValue(key);
    }
}
