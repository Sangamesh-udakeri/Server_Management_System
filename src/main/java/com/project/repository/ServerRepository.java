package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Server;
import java.util.List;


public interface ServerRepository extends JpaRepository<Server, Long> {
	
		Server findByIpAddress(String ipAddress);
		Server findByName(String name);
}
