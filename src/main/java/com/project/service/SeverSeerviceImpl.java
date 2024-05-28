package com.project.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.model.Server;
import com.project.model.Status;
import com.project.repository.ServerRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SeverSeerviceImpl implements ServerService {

	@Autowired
	private final ServerRepository serverRepository = null;

	@Override
	public Server create(Server server) {
		log.info("saving new server : {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepository.save(server);
	}

	@Override
	public Collection<Server> List(int limit) {
		log.info("fetching all servers");
		return serverRepository.findAll(PageRequest.of(0, limit)).toList();
	}

	@Override
	public Server get(Long id) {
		log.info("fetching server by id : {}", id);
		return serverRepository.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("updating server : {}", server.getName());
		return serverRepository.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("deleting server : {}", id);
		serverRepository.deleteById(id);
		return true;
	}

	@Override
	public Server ping(String ipAddress) throws IOException {
		log.info("pinging server IP :{}", ipAddress);
		Server server = serverRepository.findByIpAddress(ipAddress);
		InetAddress inetAddress = InetAddress.getByName(ipAddress);
		server.setStatus(inetAddress.isReachable(10000) ? Status.SERVER_UP : Status.SERVER_DOWN);
		serverRepository.save(server);
		return server;
	}
	private String setServerImageUrl() {
		String[] imagaNames= {"server1.png","server2.png","server3.png","server4.png",};
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/server/image/" + imagaNames[new Random().nextInt(4)]).toUriString();
	}

}
