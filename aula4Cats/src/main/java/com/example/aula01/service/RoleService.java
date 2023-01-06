package com.example.aula01.service;

import java.util.List;

import com.example.aula01.modelo.Role;

public interface RoleService {

	public Role buscarRolePorId(Long id);
	
	public Role buscarRole(String role);
	
	public List<Role> listarRole();
}
