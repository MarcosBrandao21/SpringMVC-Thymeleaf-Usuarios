package com.example.aula01.service;

import java.util.List;

import com.example.aula01.modelo.Usuario;

public interface UsuarioService {
	
	public void apagarUsuarioPorId(Long id);
	
	public Usuario buscarUsuarioPorId(Long id);
	
	public Usuario buscarUsuarioPorLogin(String login);
	
	public Usuario gravarUsuario(Usuario usuario);
	
	public void alterarUsuario(Usuario usuario);
	
	public List<Usuario> listarUsuario();
	
	public void atribuirPapelParaUsuario(Long idUsuario, int[] idsPapeis, boolean isAtivo);
	
}
