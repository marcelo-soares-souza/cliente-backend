package com.surittec.webservices.surittecbackend.cliente;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin(origins = "*")
public class ClienteResource {
	@Autowired
	private ClienteRepository clienteRepository;
		
	@GetMapping(path = "/users/{username}/clientes")
	public List<Cliente> getAllclientes(@PathVariable String username) {
		return clienteRepository.findAll();
	}

	@GetMapping(path = "/users/{username}/clientes/{id}")
	public Cliente getCliente(@PathVariable String username, @PathVariable long id) {
		return clienteRepository.findById(id).get();
	}

	@DeleteMapping(path = "/users/{username}/clientes/{id}")
	public ResponseEntity<Void> deleteCliente(@PathVariable String username, @PathVariable long id) {
		clienteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(path = "/users/{username}/clientes/{id}")
	public ResponseEntity<Cliente> updateCliente(@PathVariable String username, @PathVariable long id,
			@RequestBody Cliente cliente) {

		cliente.setUsername(username);
		Cliente clienteUpdated = clienteRepository.save(cliente);

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@PostMapping(path = "/users/{username}/clientes")
	public ResponseEntity<Void> createCliente(@PathVariable String username, @RequestBody Cliente cliente) {

		cliente.setUsername(username);
		Cliente createdCliente = clienteRepository.save(cliente);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCliente.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

}