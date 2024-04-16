package com.example.azureSLA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.microsoft.applicationinsights.attach.ApplicationInsights;

@SpringBootApplication
@EnableConfigurationProperties
public class AzureSlaApplication {

    public static void main(String[] args) {

        // configureApplicationInsights();
        ApplicationInsights.attach();

        String tenantId = System.getenv("AZURE_TENANT_ID");
        String clientId = System.getenv("AZURE_CLIENT_ID");
        String clientSecret = System.getenv("AZURE_CLIENT_SECRET");

        String keyVaultName = "slavault";
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";
        ClientSecretCredential cc = new ClientSecretCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .clientSecret(clientSecret).build();

        // Use the Azure credential to authenticate with Azure
        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(cc)
                .buildClient();

        // Retrieve the secret
        KeyVaultSecret retrievedSecret = secretClient.getSecret("DBConnectionString");

        // Access the secret value
        String secretVal = retrievedSecret.getValue();
        String secretValues[] = secretVal.split(",");

        // Now you can use the secret value in your application
        System.out.println("Secret Value: " + secretVal);

        System.setProperty("spring.datasource.url", secretValues[0]);
        System.setProperty("spring.datasource.username", secretValues[1].split("=")[1]);
        System.setProperty("spring.datasource.password", secretValues[2].split("=")[1]);

        SpringApplication.run(AzureSlaApplication.class, args);

    }
}