package com.example.aula01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aula01.modelo.Role;
import com.example.aula01.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository repository;

	@Override
	public Role buscarRolePorId(Long id) {
		Optional<Role> role = repository.findById(id);
		if(role.isPresent()) {
			return role.get();
		}else {
			throw new IllegalArgumentException("Role com id: " + id + "não existe!");
		}
	}

	@Override
	public Role buscarRole(String role) {
		return repository.findByRole(role);
	}

	@Override
	public List<Role> listarRole() {
		return repository.findAll();
	}

}
