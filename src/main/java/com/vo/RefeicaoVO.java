package com.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RefeicaoVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
		private Date data;
		private String dataString;
		private String alimento; //alimentos da refeição - cada alimento uma linha
		private String diaSemana;
		private List<String> alimentos;
		private List<String> alimentosReservados;
		private List<Boolean> reservas;
		private boolean status; // colocar em reserva

		public RefeicaoVO() {
		}
		
		public Date getData() {
			return data;
		}
		public void setData(Date data) {
			this.data = data;
		}
		public String getDataString() {
			return dataString;
		}
		public void setDataString(String dataString) {
			this.dataString = dataString;
		}
		public String getAlimento() {
			return alimento;
		}
		public void setAlimento(String alimento) {
			this.alimento = alimento;
		}
		public String getDiaSemana() {
			return diaSemana;
		}
		public void setDiaSemana(String diaSemana) {
			this.diaSemana = diaSemana;
		}
		
		public List<String> getAlimentos() {
			return alimentos;
		}

		public void setAlimentos(List<String> alimentos) {
			this.alimentos = alimentos;
		}
		
		public List<String> getAlimentosReservados() {
			return alimentosReservados;
		}

		public void setAlimentosReservados(List<String> alimentosReservados) {
			this.alimentosReservados = alimentosReservados;
		}
		
		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}
		
		public List<Boolean> getReservas() {
			return reservas;
		}

		public void setReservas(List<Boolean> reservas) {
			this.reservas = reservas;
		}
		
}