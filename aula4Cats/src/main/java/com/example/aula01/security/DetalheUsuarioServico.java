package com.example.aula01.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.aula01.modelo.Role;
import com.example.aula01.modelo.Usuario;
import com.example.aula01.repository.UsuarioRepository;

@Service
@Transactional
public class DetalheUsuarioServico implements UserDetailsService {

	private UsuarioRepository usuarioRepository;

	public DetalheUsuarioServico(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByLogin(username);

		if (usuario != null && usuario.isAtivo()) {
			Set<GrantedAuthority> papeisDoUsuario = new HashSet<GrantedAuthority>();
			for (Role role : usuario.getRoles()) {
				GrantedAuthority pp = new SimpleGrantedAuthority(role.getRole());
				papeisDoUsuario.add(pp);
			}
			User user = new User(usuario.getLogin(), usuario.getPassword(), papeisDoUsuario);
			return user;
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
	}

}
