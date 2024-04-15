package com.example.azureSLA;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import javax.xml.validation.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.applicationinsights.attach.ApplicationInsights;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
public class AzureSlaApplication {


	// public static void configureApplicationInsights() {
    //     Properties properties = new Properties();
    //     try (InputStream inputStream = AzureSlaApplication.class.getClassLoader().getResourceAsStream("connection-string-file.txt")) {
    //         if (inputStream != null) {
    //             properties.load(inputStream);
    //             String instrumentationKey = properties.getProperty("InstrumentationKey");
    //             if (instrumentationKey != null && !instrumentationKey.isEmpty()) {
    //                 System.setProperty("APPLICATION_INSIGHTS_CONNECTION_STRING", "InstrumentationKey=" + instrumentationKey);
    //             } else {
    //                 throw new IllegalArgumentException("Instrumentation key not found in connection-string-file.txt");
    //             }
    //         } else {
    //             throw new IOException("connection-string-file.txt not found in classpath");
    //         }
    //     } catch (IOException e) {
    //         throw new IllegalStateException("Error loading connection string from file", e);
    //     }
    // }

	public static void main(String[] args) {
		//configureApplicationInsights();
		ApplicationInsights.attach();
		SpringApplication.run(AzureSlaApplication.class, args);
		

	}

	// public static void main(String[] args) {
    //     System.setProperty("applicationinsights.runtime-attach.configuration.classpath.file", "applicationinsights.json");
    // 	ApplicationInsights.attach();
    //     SpringApplication.run(AzureSlaApplication.class, args);
    // }


}
// 	@GetMapping("/user")
//     public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
// 		Map<String, Object> hm = new HashMap<String, Object>(); 
// 		hm.put("name", principal.getAttribute("name"));
// 		hm.put("email", principal.getAttribute("email"));
// 		List<GrantedAuthority> authorities = new ArrayList<>(principal.getAuthorities());

//         // Access roles as needed
//         List<String> roles = authorities.stream()
//                 .map(GrantedAuthority::getAuthority)
//                 .collect(Collectors.toList());

// 		// try {
// 		// 	Connection connection = dataSource.getConnection();
// 		// 	String s= connection.getSchema();
			

// 		// } catch (SQLException e) {
// 		// 	// TODO Auto-generated catch block
// 		// 	e.printStackTrace();
// 		// }  
		
// 		// try (Connection connection = dataSource.getConnection()) {
//         //     // The SQL query to insert a user into the Users table
//         //     String insertQuery = "INSERT INTO dbo.Users (UserId,FirstName, LastName, Password, Email, Source) VALUES (1,?, ?, ?, ?, ?)";

//         //     // Sample user data
//         //     String firstName = principal.getAttribute("given_name");
//         //     String lastName =  principal.getAttribute("family_name");
//         //     String password =  "TestingPasswordforInsert";
//         //     String email = principal.getAttribute("email");
//         //     String source = "Azure";

//         //     try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
//         //         // Set parameters for the insert query
//         //         preparedStatement.setString(1, firstName);
//         //         preparedStatement.setString(2, lastName);
//         //         preparedStatement.setString(3, password);
//         //         preparedStatement.setString(4, email);
//         //         preparedStatement.setString(5, source);

//         //         // Execute the insert query
//         //         int rowsAffected = preparedStatement.executeUpdate();

//         //         // Check if the insertion was successful
//         //         if (rowsAffected > 0) {
//         //             System.out.println("User inserted successfully.");
//         //         } else {
//         //             System.out.println("Failed to insert user.");
//         //         }
//         //     }
//         // } catch (SQLException e) {
//         //     e.printStackTrace();
//         // }

// 		String connectionString = "DefaultEndpointsProtocol=https;AccountName=slaticketmanagement;AccountKey=ftlncJZom86F3sjmxu+PWVgCKxPJvKjl8LJWGSAKyC8sHj1WAkLG+PRghFpfTNDoAt+C6DEle6z1+AStSJfu8w==;EndpointSuffix=core.windows.net";
//         // Replace with your actual blob container name
//         String containerName = "slaticketmanagement";
//         // Replace with the user's name and ticket ID
//         String userName = "john_doe";
//         int ticketId = 123;

// 		try {
//             // Create a BlobServiceClient
//             BlobServiceClientBuilder builder = new BlobServiceClientBuilder().connectionString(connectionString);

//             // Create a Blob Container Client
//             BlobContainerClient containerClient = new BlobContainerClientBuilder()
//                     .connectionString(connectionString)
//                     .containerName(containerName)
//                     .buildClient();

			

//             // Create a user folder (directory)
//             String userDirectory = userName + "/";
            
//             // Create a ticket subfolder (directory)
//             String ticketDirectory = userDirectory + "ticket_" + ticketId + "/";
            
//             // Use the directory information when uploading attachments
//             String attachmentFileName = "G:\\My Files\\UCM\\Masters\\IntroductionToCloudComputing\\GroupProject\\Database\\SLATicketManagementSytem_Database.txt";

// 			String blobUrl = uploadAttachment(containerClient, ticketDirectory, attachmentFileName);

//             System.out.println("Blob URL for the uploaded attachment: " + blobUrl);
			
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
// 		return hm;
//     }

//     private static String uploadAttachment(BlobContainerClient containerClient, String directory, String fileName) {
//         // Combine the directory and file name to create the blob name
//         String blobName = directory + fileName;

//         // Upload attachment to the specified blob name
//         containerClient.getBlobClient(blobName).uploadFromFile(fileName);

//         // Construct the Blob URL
//         return containerClient.getBlobClient(blobName).getBlobUrl();
//     }


		

//     //}

// 	@RequestMapping("/logout")
//     public void logout(HttpServletRequest request, HttpSecurity http) throws Exception {
// 		http.logout(l -> l.logoutSuccessUrl("/").permitAll());
//         //request.logout();
//         //return "redirect:/";
//     }

// }
