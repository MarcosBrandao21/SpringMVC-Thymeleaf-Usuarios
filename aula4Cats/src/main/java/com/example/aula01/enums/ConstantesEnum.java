package com.example.aula01.enums;

public class ConstantesEnum {
	
	public enum AcessosEnum {
		URL_USER("/auth/user/*"),
		URL_ADMIN("/auth/admin/*"),
		URL_BIBLIOTECARIO("/auth/bibliotecario/*");
		
		private String getValor;
		
		AcessosEnum(String getValor) {
			this.getValor = getValor;
		}
		
		public String getgetValor() {
			return getValor;
		}
	}

	public enum AlertaEnum {
		MENSAGEM("mensagem");
		
		private String getValor;
		
		AlertaEnum(String getValor) {
			this.getValor = getValor;
		}
		
		public String getgetValor() {
			return getValor;
		}
	}
}
