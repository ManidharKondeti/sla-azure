package com.example.azureSLA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.microsoft.applicationinsights.attach.ApplicationInsights;

@SpringBootApplication
public class AzureSlaApplication {
	public static void main(String[] args) {
        
		//configureApplicationInsights();
		ApplicationInsights.attach();

        String keyVaultName = "slavault";
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";
        ClientSecretCredential cc = new ClientSecretCredentialBuilder()
                                    .tenantId("08d2293e-cece-4727-890b-cab1fadeed57")
                                    .clientId("17b68e0d-04e3-47d9-9a0d-cf8c83203e59")
                                    .clientSecret("wRC8Q~5Pj0M5k_CQBt3HG41Zbz7nekjqLhsj3bLl").build();
        
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
        System.setProperty("spring.datasource.pwd", secretValues[2].split("=")[1]);

		SpringApplication.run(AzureSlaApplication.class, args);
		
	}
}