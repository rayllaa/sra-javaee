package com.vo;

import java.io.Serializable;

public class AdministradorVO extends UsuarioVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String funcao;
	
	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

}
