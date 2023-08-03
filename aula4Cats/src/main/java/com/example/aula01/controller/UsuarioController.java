package com.example.aula01.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.aula01.enums.ConstantesEnum;
import com.example.aula01.enums.RolesEnum;
import com.example.aula01.modelo.Role;
import com.example.aula01.modelo.Usuario;
import com.example.aula01.service.RoleService;
import com.example.aula01.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RoleService roleService;
	
	private static final String MODEL_USUARIO = "usuario";
	
	private static final String RETURN_CRIAR_USUARIO= "/publica-criar-usuario";
	
	private static final String REDIRECT_LISTAR = "redirect:/usuario/admin/listar";
	
	private static final String RETURN_ALTERAR_URUARIO = "/auth/user/user-alterar-usuario";

	@GetMapping("/index")
	public String index(@CurrentSecurityContext(expression = "authentication.name") String login) {

		Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);

		String redirectURL = "";
		if (temAutorizacao(usuario, RolesEnum.ADMIN.getgetValor())) {
			redirectURL = "/auth/admin/admin-index";
		} else if (temAutorizacao(usuario, RolesEnum.USER.getgetValor())) {
			redirectURL = "/auth/user/user-index";
		} else if (temAutorizacao(usuario, RolesEnum.BIBLIOTECARIO.getgetValor())) {
			redirectURL = "/auth/bibliotecario/bibliotecario-index";
		}
		return redirectURL;
	}

	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		model.addAttribute(MODEL_USUARIO, new Usuario());
		return RETURN_CRIAR_USUARIO;
	}

	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, Model model,
			RedirectAttributes attributes) {

		if (result.hasErrors())
			return RETURN_CRIAR_USUARIO;

		if (usuarioService.buscarUsuarioPorLogin(usuario.getLogin()) != null) {
			model.addAttribute("loginExiste", "Login já existe cadastrado");
			return RETURN_CRIAR_USUARIO;
		}
		usuarioService.gravarUsuario(usuario);

		attributes.addFlashAttribute(ConstantesEnum.AlertaEnum.MENSAGEM.getgetValor(), 
									 "Usuario salvo com sucesso!");
		return "redirect:/usuario/novo";
	}

	@RequestMapping("/admin/listar")
	public String listarUsuario(Model model) {
		model.addAttribute("usuarios", usuarioService.listarUsuario());
		return "/auth/admin/admin-listar-usuario";
	}

	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		usuarioService.apagarUsuarioPorId(id);
		
		return REDIRECT_LISTAR;
	}

	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		model.addAttribute(MODEL_USUARIO, usuario);

		return RETURN_ALTERAR_URUARIO;
	}

	@PostMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			usuario.setId(id);
			return RETURN_ALTERAR_URUARIO;
		}
		usuarioService.alterarUsuario(usuario);
		return REDIRECT_LISTAR;
	}

	@GetMapping("/editarRole/{id}")
	public String selecionarRole(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		List<Role> roles = roleService.listarRole();
		model.addAttribute(MODEL_USUARIO, usuario);
		model.addAttribute("listaRoles", roles);
		return "/auth/admin/admin-editar-role-usuario";
	}

	@PostMapping("/editarRole/{id}")
	public String atribuirRole(@PathVariable("id") long idUsuario,
			@RequestParam(value = "rls", required = false) int[] rls, Usuario usuario, RedirectAttributes attributes) {
		if (rls == null || rls.length > 1) {
			usuario.setId(idUsuario);
			attributes.addFlashAttribute(ConstantesEnum.AlertaEnum.MENSAGEM.getgetValor(), 
										 "Uma Role deve selecionada!");
			return "redirect:/usuario/editarRole/" + idUsuario;
		} else {
			usuarioService.atribuirPapelParaUsuario(idUsuario, rls, usuario.isAtivo());
		}
		return REDIRECT_LISTAR;
	}

	private boolean temAutorizacao(Usuario usuario, String role) {
		for (Role rl : usuario.getRoles()) {
			if (rl.getRole().equals(role)) {
				return true;
			}
		}
		return false;
	}

}
