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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String role;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private List<Usuario> usuarios;

	public Role(String role) {
		super();
		this.role = role;
	}
	
	public Role(Long id_role, String role) {
		super();
		this.role = role;
	}
}
