package delphi.backend.service.secrets;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.secretsmanager.model.*;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;

import delphi.backend.constant.RegistrationConstant;

@Singleton
public class SecretsManager {

    private static final Logger LOG = LoggerFactory.getLogger(SecretsManager.class.getName());


    // SNIPPET FROM AWS Documentation
    // If you need more information about configurations or implementing the sample code, visit the AWS docs:
    // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites
    public static String getSecret() {
        String secretName = RegistrationConstant.SECRETS_NAME;
        String region = "us-west-2";

        // Create a Secrets Manager client
        AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        return client.getSecretValue(getSecretValueRequest).getSecretString();
    }
}
