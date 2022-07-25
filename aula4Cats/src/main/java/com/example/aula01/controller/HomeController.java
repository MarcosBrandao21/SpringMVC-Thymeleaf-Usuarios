package com.example.aula01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("msnBenVindo", "Bem-vindo á biblioteca");
		return "publica-index";
	}

}
