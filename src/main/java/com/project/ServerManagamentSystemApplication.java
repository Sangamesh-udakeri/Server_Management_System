package com.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.model.Server;
import com.project.model.Status;
import com.project.repository.ServerRepository;

@SpringBootApplication
public class ServerManagamentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerManagamentSystemApplication.class, args);
	}
	
	CommandLineRunner run(ServerRepository serverRepository) {
		
		return args->{
			serverRepository.save(new Server(null, "ubuntu", "16 GB", "192.168.1.168", "PC", "http://localhost:8080/server/images/server1.png",Status.SERVER_UP ));
			serverRepository.save(new Server(null, "Fedora", "16 GB", "192.168.1.58", "PC", "http://localhost:8080/server/images/server1.png",Status.SERVER_UP ));
			serverRepository.save(new Server(null, "Ms 2008", "16 GB", "192.168.1.21", "PC", "http://localhost:8080/server/images/server1.png",Status.SERVER_UP ));
			serverRepository.save(new Server(null, "Red hat", "16 GB", "192.168.1.14", "PC", "http://localhost:8080/server/images/server1.png",Status.SERVER_UP ));
		};
	}

}
