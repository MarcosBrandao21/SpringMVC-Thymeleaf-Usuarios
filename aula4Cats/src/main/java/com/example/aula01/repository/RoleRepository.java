package com.example.aula01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aula01.modelo.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);
}
