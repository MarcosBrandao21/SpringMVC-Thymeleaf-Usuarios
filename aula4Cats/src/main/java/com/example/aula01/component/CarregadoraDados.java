package com.example.aula01.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.aula01.modelo.Role;
import com.example.aula01.repository.RoleRepository;

@Component
public class CarregadoraDados implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {

		String[] roles = { "ADMIN", "USER", "BIBLIOTECARIO" };

		for (String roleString : roles) {
			Role role = roleRepository.findByRole(roleString);
			if (role == null) {
				role = new Role(roleString);
				roleRepository.save(role);
			}
		}

	}

}
