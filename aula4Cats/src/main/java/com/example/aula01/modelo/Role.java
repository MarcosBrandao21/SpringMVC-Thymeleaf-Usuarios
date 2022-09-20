package com.example.aula01.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String role;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<Usuario> usuarios;

	public Role() {}

	public Role(String role) {
		super();
		this.role = role;
	}
}
