package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import com.dao.DaoRefeicao;
import com.dao.DaoReserva;
import com.vo.AlimentoVO;
import com.vo.RefeicaoVO;
import com.vo.ReservaVO;
import com.vo.UsuarioVO;

@ManagedBean
@ViewScoped
@SessionScoped
public class ReservaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private DaoReserva dao_reserva;
	 private DaoRefeicao dao_refeicao;
	 private static UsuarioVO usuario;
	 private RefeicaoBean ref_bean;
	 private List<ReservaVO> reservas;
	 private List<ReservaVO> reservasAlunos;
	 private List<RefeicaoVO> refeicaoReservada;
	 private Boolean selecionarItem;
	 private String valueDefault = "refeicao";
	 private String selectedRadio;
	 
	public ReservaBean() {
		dao_reserva = new DaoReserva();
		dao_refeicao = new DaoRefeicao();
		ref_bean = new RefeicaoBean();
		reservasAlunos = new ArrayList<ReservaVO>();
	    usuario = UsuarioBean.getSessao();
	}
	
	public String getValueDefaultRadio() {
		return valueDefault;
	}
	
	public void setValueDefaultRadio(String valueDefault) {
		this.valueDefault = valueDefault;
	}
	
	public String getSelectedRadio() {
		return selectedRadio;
	}

	public void setSelectedRadio(String selectedRadio) {
		this.selectedRadio = selectedRadio;
	}

	public Boolean getSelecionarItem() {
		return selecionarItem;
	}

	public void setSelecionarItem(Boolean selecionarItem) {
		this.selecionarItem = selecionarItem;
	}
	
	public List<RefeicaoVO> getRefeicaoReservada() {
		return refeicaoReservada;
	}

	public void setRefeicaoReservada(List<RefeicaoVO> refeicaoReservada) {
		this.refeicaoReservada = refeicaoReservada;
	}
	
	public void reserva() {
		boolean result = false;
		boolean result2 = false;
		List<RefeicaoVO> refeicoes = getRefeicaoReservada();

		for(int i  = 0; i < refeicoes.size(); i++) {
			RefeicaoVO r = refeicoes.get(i);
			System.out.println(r.getDataString()+" "+r.getDiaSemana()+" "+r.getAlimentosReservados().size());
			
			if(r.getAlimentosReservados().size() == 0) { //aluno ckeckou a refeicao mas não especificou alimento, salva todos alimentos da refeicao
				
				List<AlimentoVO> lista = dao_refeicao.getAlimentoData(r.getData());
										
					for(int x = 0; x < lista.size(); x++) {
						AlimentoVO a = lista.get(x);
						boolean reserva = dao_reserva.existenciaReservaAluno(r.getData(), a.getDescricao(), usuario.getLogin());

						if(!reserva) { //adiciona reserva apenas se o aluno ainda não tiver reservado
							result =	dao_reserva.addReserva(r.getData(), a.getDescricao(), usuario.getLogin());
						}
						
					}
			}			
			else{
				//adiciona cada alimento reservado pelo usuario
				for(int j = 0; j < r.getAlimentosReservados().size(); j++) {
					String alimento = r.getAlimentosReservados().get(j);
					boolean reserva = dao_reserva.existenciaReservaAluno(r.getData(), alimento, usuario.getLogin());
					
					if(!reserva) { //adiciona reserva apenas se o aluno ainda não tiver reservado
						result2 =	dao_reserva.addReserva(r.getData(), alimento, usuario.getLogin());
					}
				}
			}
		}
		
		if(result || result2) {
			reservado();
		}
		
	}
	
	public List<ReservaVO> quantidadeReservaAlimento() { // data refeicao, alimentos, e a quantidade de reservas
		
		List<RefeicaoVO> refeicoes = ref_bean.getRefeicoes();
		
		reservas = new ArrayList<ReservaVO>();

		for(int i = 0; i < refeicoes.size(); i++) {
			RefeicaoVO r = refeicoes.get(i);
			
			List<String> alimento = r.getAlimentos();

			for(int j = 0; j < alimento.size(); j++) {// quantidade de resreva de cada alimento em cada refeicao (data)

				String a = alimento.get(j);
				int  quantidadeReserva = dao_reserva.reservaAlimento(r.getData(), a);
				
				ReservaVO ref = new ReservaVO(r.getDiaSemana(), r.getDataString(), a, quantidadeReserva);
				reservas.add(ref);
			}
		}
		
		return reservas;
	}
	
	public List<ReservaVO> quantidadeReservaRefeicao() { // data refeicao, alimentos, e a quantidade de reservas
		List<ReservaVO> reservas_ref = new ArrayList<ReservaVO>();
		
		List<RefeicaoVO> refeicoes = dao_refeicao.getRefeicoesData(); //obtem datas das refeicoes
		
		for(int i = 0; i < refeicoes.size(); i++) {
			int reservas = 0;
			
			RefeicaoVO r = refeicoes.get(i);
			ReservaVO res = new ReservaVO();

			reservas = dao_reserva.reservaRefeicao(r.getData());//obter reservas
			
			res.setQuantidadeReserva(reservas);
			res.setDataRefeicaoString(r.getDataString());
			res.setDiaSemana(r.getDiaSemana());
			
			reservas_ref.add(res);
			
			}
		
		return reservas_ref;
	}

	public List<ReservaVO> reservaAlunos() {// login,  alimentos, data refeição e data reserva
		 
		 reservas = dao_reserva.reservasAlunos();
		 List<String> alimentos;
			for(int i = 0; i < reservas.size(); i++) {
				ReservaVO r1 = reservas.get(i);
				
				String a = "   ";
				alimentos = new ArrayList<String>();
				alimentos.add(r1.getDescricaoAlimento());
				a += r1.getDescricaoAlimento()+"     ";
				
				int j = i + 1;
				while( j < reservas.size()) {
					ReservaVO r2 = reservas.get(j);
						 if(r1.getLogin_aluno().equals(r2.getLogin_aluno()) && r1.getDataRefeicaoString().equals(r2.getDataRefeicaoString())) {
							alimentos.add(r2.getDescricaoAlimento());
							a += r2.getDescricaoAlimento()+"   ";

							reservas.remove(j);
						 } 
						 else {
							 j++; //add o indice só quando a data for diferente, pois enquanto é igual é apagado elementos e o indice permanece o mesmo
						 }	
				}
				
				ReservaVO r = new ReservaVO();
				r.setLogin_aluno(r1.getLogin_aluno());
				r.setDataRefeicaoString(r1.getDataRefeicaoString());
				r.setDataReservaString(r1.getDataReservaString());
				r.setDescricaoAlimento(a);
				r.setAlimentos(alimentos);
				reservasAlunos.add(r);
				
			}
		
			return reservasAlunos;	
	 }

	 
	public void reservado() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva(s) realizada(s) com sucesso", "!"));
	}

}
