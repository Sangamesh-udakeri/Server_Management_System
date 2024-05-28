package com.project;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Response;
import com.project.model.Server;
import com.project.model.Status;
import com.project.service.ServerService;

@RestController
@RequestMapping("/server")
public class ServerController {

	@Autowired
	private final ServerService serverService = null;
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

	@GetMapping("/list")
	public ResponseEntity<Response> getServers() {
		Response response = Response.builder().timeStamp(LocalDateTime.now()).statusCode(HttpStatus.OK.value())
				.status(HttpStatus.OK).message("Servers retrieved successfully")
				.data(Map.of("servers", serverService.List(30))).build();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable String ipAddress)
			throws UnknownHostException, IOException {
		try {
			Server server = serverService.ping(ipAddress);
			String message = server.getStatus() == Status.SERVER_UP ? "ping success" : "ping failed";
			return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).message(message)
					.data(Map.of("server", server)).status(HttpStatus.OK).build());
		} catch (Exception e) {
			logger.error("Error pinging server: {}", ipAddress, e);
			return ResponseEntity.internalServerError()
					.body(Response.builder().timeStamp(LocalDateTime.now()).message("Error pinging server").build());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody Server server) {
		try {

			String message = "server created";
			return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).message(message)
					.data(Map.of("server", serverService.create(server))).status(HttpStatus.CREATED).build());
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(Response.builder().timeStamp(LocalDateTime.now()).message("Error pinging server").build());
		}
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable Long id) {
		try {
			String message = "server retrieved";
			return ResponseEntity.ok(Response.builder().timeStamp(LocalDateTime.now()).message(message)
					.data(Map.of("server", serverService.get(id))).status(HttpStatus.OK).build());
		} catch (Exception e) {
			return ResponseEntity.internalServerError()
					.body(Response.builder().timeStamp(LocalDateTime.now()).message("Error retrieving server").build());
		}
	}

	@DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable Long id) {
        try {
            String message = "server deleted";
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .message(message)
                    .data(Map.of("delete", serverService.delete(id)))
                    .status(HttpStatus.OK)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Error deleting server")
                            .build());
        }
    }

	@GetMapping(path = "/image/{fileName}")
	public byte[] getServerImage(@RequestParam String fileName) throws IOException {
		return Files.readAllBytes(Paths.get(System.getProperty("F") + "serverimages/" + fileName));
	}
}
