package com.project.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;

import com.project.model.Server;

public interface ServerService {

	Server create(Server server);
	Server ping(String ipAddress) throws UnknownHostException, IOException;
	Collection<Server> List(int limit);
	Server get(Long id);
	Server update(Server server);
	Boolean delete(Long id);
	
}
