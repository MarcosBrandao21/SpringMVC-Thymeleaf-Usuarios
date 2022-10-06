package com.example.aula01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.aula01.modelo.Role;
import com.example.aula01.modelo.Usuario;
import com.example.aula01.repository.RoleRepository;
import com.example.aula01.repository.UsuarioRepository;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private final String USER = "USER";
	
	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/publica-criar-usuario";
	}
	
	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result,
			Model model ,RedirectAttributes attributes) {
		
		if (result.hasErrors()) return "/publica-criar-usuario";
		
		if (usuarioRepository.findByLogin(usuario.getLogin()) != null) {
			model.addAttribute("loginExiste", "Login já existe cadastrado");
			return "/publica-criar-usuario";
		};
		
		Role role = roleRepository.findByRole(USER);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		usuario.setRoles(roles);
		
		usuarioRepository.save(usuario);
		attributes.addFlashAttribute("mensagem", "Usuario salvo com sucesso!");
		return "redirect:/usuario/novo";
	}
	
	@RequestMapping("/admin/listar")
	public String listarUsuario(Model model) {
		model.addAttribute("usuarios", usuarioRepository.findAll());
		return "/auth/admin/admin-listar-usuario";
	}
	
	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Id invalido: " + id));
		usuarioRepository.delete(usuario);
		return "redirect:/usuario/admin/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model) {
		Optional<Usuario> usuarioAntigo = usuarioRepository.findById(id);
		if(!usuarioAntigo.isPresent()) {
			throw new IllegalArgumentException("Usuario invalido: " + id);
		}
		Usuario usuario = usuarioAntigo.get();
		model.addAttribute("usuario", usuario);
		return "/auth/user/user-alterar-usuario";
	}
	
	@PostMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result) {
		if(result.hasErrors()) {
			usuario.setId(id);
			return "/auth/user/user-alterar-usuario";
		}
		usuarioRepository.save(usuario);
		return "redirect:/usuario/admin/listar";
	}
	
	@GetMapping("/editarRole/{id}")
	public String selecionarRole (@PathVariable("id") long id, Model model) {
		Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
		if (!usuarioExistente.isPresent()) {
			throw new IllegalArgumentException("Usuario invalido: " + id);
		}
		Usuario usuario = usuarioExistente.get();
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaRoles", roleRepository.findAll());
		return "/auth/admin/admin-editar-role-usuario";
	}
	
	@PostMapping("/editarRole/{id}")
	public String atribuirRole(@PathVariable("id") long idUsuario,
							   @RequestParam(value = "rls", required = false) int[] rls,
							   Usuario usuario, RedirectAttributes attributes ) {
		if(rls == null) {
			usuario.setId(idUsuario);
			attributes.addFlashAttribute("mensagem", "Pelo menos uma role deve ser selecionada!");
			return "redirect:/usuario/editarRole/"+idUsuario;
		}else {
			List<Role> roles = new ArrayList<Role>();
			for (int i = 0; i < rls.length; i++) {
				long idRole = rls[i];
				Optional<Role> roleOptional = roleRepository.findById(idRole);
				if(roleOptional.isPresent()) {
					Role role = roleOptional.get();
					roles.add(role);
				}
			}
			Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
			if(usuarioOptional.isPresent()) {
				Usuario usuarioRecebe = usuarioOptional.get();
				usuarioRecebe.setRoles(roles); //relaciona roles ao usuario
				usuarioRepository.save(usuarioRecebe);
			}
		}
		return "redirect:/usuario/admin/listar";
	}

}
