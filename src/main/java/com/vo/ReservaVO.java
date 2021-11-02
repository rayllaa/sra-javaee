package com.vo;

import java.util.Date;
import java.util.List;

public class ReservaVO {
	private Date dataRefeicao;
	private String dataRefeicaoString;
	private String diaSemana;
	private int quantidadeReserva;
	private String descricaoAlimento;
	private Date dataReserva;
	private String dataReservaString;
	private String login_aluno;
	private List<String> alimentos;
	private List<Boolean> reservas;

	public ReservaVO() {
		
	}
	
	public ReservaVO(String diaSemana, String dataString, String descricao_alimento, int quantidadeReserva) {
		super();
		this.diaSemana = diaSemana;
		this.dataRefeicaoString = dataString;
		this.quantidadeReserva = quantidadeReserva;
		this.descricaoAlimento = descricao_alimento;
	}
	
	public Date getDataRefeicao() {
		return dataRefeicao;
	}
	public void setDataRefeicao(Date dataRefeicao) {
		this.dataRefeicao = dataRefeicao;
	}
	public String getDataRefeicaoString() {
		return dataRefeicaoString;
	}
	public void setDataRefeicaoString(String dataString) {
		this.dataRefeicaoString = dataString;
	}
	public String getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}
	public int getQuantidadeReserva() {
		return quantidadeReserva;
	}
	public void setQuantidadeReserva(int quantidadeReserva) {
		this.quantidadeReserva = quantidadeReserva;
	}
	public Date getDataReserva() {
		return dataReserva;
	}
	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}
	public String getDataReservaString() {
		return dataReservaString;
	}
	public void setDataReservaString(String dataReservaString) {
		this.dataReservaString = dataReservaString;
	}
	public String getDescricaoAlimento() {
		return descricaoAlimento;
	}
	public void setDescricaoAlimento(String descricaoAlimento) {
		this.descricaoAlimento = descricaoAlimento;
	}
	public String getLogin_aluno() {
		return login_aluno;
	}
	public void setLogin_aluno(String login_aluno) {
		this.login_aluno = login_aluno;
	}
	public List<String> getAlimentos() {
		return alimentos;
	}
	public void setAlimentos(List<String> alimentos) {
		this.alimentos = alimentos;
	}
	public List<Boolean> getReservas() {
		return reservas;
	}
	public void setReservas(List<Boolean> reservas) {
		this.reservas = reservas;
	}

}
