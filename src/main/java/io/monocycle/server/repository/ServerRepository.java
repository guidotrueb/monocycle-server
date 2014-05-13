package io.monocycle.server.repository;

import io.monocycle.server.model.Server;

import org.springframework.data.repository.CrudRepository;

public interface ServerRepository extends CrudRepository<Server, Long> {

	Server findById(Long id);
	
    Server findByHostname(String hostname);
    
}