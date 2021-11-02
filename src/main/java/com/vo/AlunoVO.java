package com.vo;

import java.io.Serializable;

public class AlunoVO extends UsuarioVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String prontuario;
	
	public AlunoVO() {
		
	}
	
	public AlunoVO(String login, String senha, String nome, String email, String cpf, String rg, String celular, String prontuario) {
		super.login = login;
		super.senha = senha;
		super.nome = nome;
		super.email = email;
		super.cpf = cpf;
		super.rg = rg;
		super.celular = celular;
		this.prontuario = prontuario;
	}
	
	public String getProntuario() {
		return prontuario;
	}
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	
}
