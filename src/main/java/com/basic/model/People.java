package com.basic.model;

public abstract class People {
	private final String name;
	private final Cpf cpf;
	private final Email email;

	public People(String name, Cpf cpf, Email email) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public Cpf getCpf() {
		return cpf;
	}

	public Email getEmail {
		return email;
	}
}
