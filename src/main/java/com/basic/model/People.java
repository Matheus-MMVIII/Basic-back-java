package com.basic.model;

public class People {
	private final String name;
	private final Cpf cpf;
	private final Email email;

	public People(String name, String cpf, String email) {
		this.name = name;
		this.cpf = new Cpf(cpf);
		this.email = new Email(email);
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf.getCpf();
	}

	public String getEmail() {
		return email.getEmail();
	}
}
