package com.example.aula01;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class ConfiguracaoVisao implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/auth/admin/admin-index").setViewName("/auth/admin/admin-index");
		registry.addViewController("/auth/user/user-index").setViewName("/auth/user/user-index");
		registry.addViewController("/auth/biblio/biblio-index").setViewName("/auth/bibliotecario/bibliotecario-index");
		registry.addViewController("/auth/auth-acesso-negado").setViewName("/auth/auth-acesso-negado");
	}
}
