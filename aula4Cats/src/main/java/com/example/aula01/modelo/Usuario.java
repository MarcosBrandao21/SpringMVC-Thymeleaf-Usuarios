package com.example.aula01.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, message = "O nome deve ter no mínimo 3 carateres")
	private String nome;
	
	@CPF(message = "CPF inválido")
	private String cpf; 
	
	@Basic
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Email(message = "Email inválido")
	private String email;
		
	@NotEmpty(message = "A senha deve ser informada")
	@Size(min = 3, message = "A senha deve ter no mínimo 3 caracteres")
	private String password;
	
	@NotEmpty(message = "O login deve ser informado")
	@Size(min = 4, message = "O login deve ter no mínimo 4 caracteres")
	private String login;
	
	private boolean ativo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuario_role",
			joinColumns = @JoinColumn(name = "usuario_id"),
			   inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
	
}
