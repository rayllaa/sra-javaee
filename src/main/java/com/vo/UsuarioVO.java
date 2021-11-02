package com.vo;

import java.io.Serializable;

public class UsuarioVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String login;
	public String senha; //Password -https://docs.oracle.com/javase/tutorial/uiswing/components/passwordfield.html
	public String nome;
	public String cpf;
	public String rg;
	public String celular;
	public String email;
		
	public UsuarioVO() {
		
	}
	
	public UsuarioVO(String login, String senha, String nome, String cpf, String rg, String celular, String email) {
		super();
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.celular = celular;
		this.email = email;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	} 
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
