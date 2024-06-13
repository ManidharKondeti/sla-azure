package com.example.azureSLA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import com.example.azureSLA.DataSource.dataSourceConfig;
import com.microsoft.applicationinsights.attach.ApplicationInsights;

@SpringBootApplication
public class AzureSlaApplication {
	public static void main(String[] args) {
        
		//configureApplicationInsights();
		ApplicationInsights.attach();

        String keyVaultName = "slavault-dbt";
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.net";
        ClientSecretCredential cc = new ClientSecretCredentialBuilder()
                                    .tenantId("08d2293e-cece-4727-890b-cab1fadeed57")
                                    .clientId("17b68e0d-04e3-47d9-9a0d-cf8c83203e59")
                                    .clientSecret("XrM8Q~eyjdfhDT5K1vORpVOOq2xKIro6W-4U.djr").build();
        
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
        System.out.println("DataSource URL: " + secretValues[0]);

        dataSourceConfig.DB_URL= secretValues[0].trim();
        dataSourceConfig.DB_USERNAME=secretValues[1].split("=")[1];
        dataSourceConfig.DB_PWD=secretValues[2].split("=")[1];

		SpringApplication.run(AzureSlaApplication.class, args);
		
	}
}
