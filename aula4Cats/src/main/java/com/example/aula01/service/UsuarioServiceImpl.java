package com.example.aula01.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.aula01.modelo.Role;
import com.example.aula01.modelo.Usuario;
import com.example.aula01.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private BCryptPasswordEncoder criptografia;

	private static String USER = "USER";

	@Override
	public void apagarUsuarioPorId(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		repository.delete(usuario);
	}

	@Override
	public Usuario buscarUsuarioPorId(Long id) {
		Optional<Usuario> usuario = repository.findById(id);
		if (usuario.isPresent()) {
			return usuario.get();
		} else {
			throw new IllegalArgumentException("Usuario com id: " + id + "não existe!");
		}
	}

	@Override
	public Usuario buscarUsuarioPorLogin(String login) {
		return repository.findByLogin(login);
	}

	@Override
	public Usuario gravarUsuario(Usuario usuario) {
		Role role = roleService.buscarRole(USER);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		usuario.setRoles(roles);

		String senhaCriptografada = criptografia.encode(usuario.getPassword());
		usuario.setPassword(senhaCriptografada);

		return repository.save(usuario);
	}

	@Override
	public void alterarUsuario(Usuario usuario) {
		repository.save(usuario);
	}

	@Override
	public List<Usuario> listarUsuario() {
		return repository.findAll();
	}

	@Override
	public void atribuirPapelParaUsuario(Long idUsuario, int[] idsRoles, boolean isAtivo) {
		List<Role> roles = new ArrayList<Role>();
		for (int i = 0; i < idsRoles.length; i++) {
			long idRole = idsRoles[i];

			Role role = roleService.buscarRolePorId(idRole);
			roles.add(role);
		}

		Usuario usuarioRecebe = buscarUsuarioPorId(idUsuario);
		usuarioRecebe.setRoles(roles); // relaciona roles ao usuario
		usuarioRecebe.setAtivo(isAtivo);
		alterarUsuario(usuarioRecebe);
	}

}
