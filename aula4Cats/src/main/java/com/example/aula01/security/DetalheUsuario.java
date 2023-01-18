package com.example.aula01.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.aula01.modelo.Role;
import com.example.aula01.modelo.Usuario;

public class DetalheUsuario implements UserDetails {

	private static final long serialVersionUID = -5139378739432852722L;
	
	private Usuario usuario;

	public DetalheUsuario(Usuario usuario) {
		super();
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles = usuario.getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Role role : roles) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role.getRole());
			authorities.add(sga);
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return usuario.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return usuario.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return usuario.isAtivo();
	}

	public String getNome() {
		if (!usuario.getNome().isEmpty()) {
			return usuario.getNome();
		}
		return "NOME DE USUARIO NÃO LOCALIZADO";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(usuario.getLogin());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetalheUsuario other = (DetalheUsuario) obj;
		return Objects.equals(usuario.getLogin(), other.usuario.getLogin());
	}

}
