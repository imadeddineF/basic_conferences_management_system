package com.example.conferenceManagement;

import com.example.conferenceManagement.controllers.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class ConferenceManagementApplication implements CommandLineRunner  {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
    	System.out.println("\033[36m<--------------------Start-------------------->\033[0m");
		System.out.println("Running!");
    	System.out.println("\033[36m<---------------------End--------------------->\033[0m");
	}
}
