package com.example.aula01.enums;

public enum RolesEnum {

	USER("USER"),
	ADMIN("ADMIN"),
	BIBLIOTECARIO("BIBLIOTECARIO");
	
	private String getValor;
	
	RolesEnum(String getValor) {
		this.getValor = getValor;
	}
	
	public String getgetValor() {
		return getValor;
	}
}
