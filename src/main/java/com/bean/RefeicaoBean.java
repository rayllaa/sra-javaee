package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dao.DaoAlimento;
import com.dao.DaoRefeicao;
import com.vo.AlimentoVO;
import com.vo.RefeicaoVO;

@ManagedBean
@ViewScoped
public class RefeicaoBean implements Serializable { 
	
	private static final long serialVersionUID = 1L;
		
	 private String[] selectedAlimentos; 
	 private String[] diaSemana; 
	 private List<String> alimentosList;
	 private List<RefeicaoVO> refeicoes;
	 
	 private List<RefeicaoVO> cardapio;
	 private List<String> alimentos;
	 private Date data;

	 private DaoAlimento dao_ali;
	 private DaoRefeicao dao_ref;
	
	
	public RefeicaoBean() {
		
		dao_ref = new DaoRefeicao();
		dao_ali = new DaoAlimento();
		alimentosList = new ArrayList<String>();
		refeicoes = new ArrayList<RefeicaoVO>();
		cardapio = new ArrayList<RefeicaoVO>();
	}
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String[] getDiaSemana(){
		return diaSemana;
	}	
	public void setDiaSemana(String[] diaSemana) {	
		this.diaSemana = diaSemana;
	}	
	public String[] getSelectedAlimentos() {
		return selectedAlimentos;
	}
	public void setSelectedAlimentos(String[] selectedAlimentos) {
		this.selectedAlimentos = selectedAlimentos;
	}
	public List<String> getAlimentosList() {
			return alimentosList;
	}
	public void setAlimentosList(List<String> alimentosList) {
		this.alimentosList = alimentosList;
	}
	public List<String> getAlimentos() {
		 alimentosList = dao_ali.listaAlimentos();
	 	return alimentosList;
	}
	
	public void salvarRefeicao() {
		 String alimento;
		 boolean resultRef = false;
		 boolean resultAl = false;
		
		 //Ponteiro nulo quando leva para campo em branco
		 
		 if(data == null || selectedAlimentos == null) { //tirar testar na interface
			 campoBranco();
			 
		 }else {
			 
			String dia = diaSemana(); //obtém dia da semana através da data	
			resultRef = dao_ref.addRefeicao(data, dia); //adiciona refeição
		 
			 for(int i = 0; i < selectedAlimentos.length; i++ ) {
				 alimento = selectedAlimentos[i];
				AlimentoVO a = dao_ali.getAlimento(alimento); // obtem objeto alimento (descricao e id)
				
					if(a.getDescricao() != null) { //testa se achou alimento
						boolean r =	dao_ref.getAlimentoRefeicao(data, a.getId());// se tem esse alimento nessa refeição
						if(!r) {
							resultAl = dao_ref.addRefeicaoAlimento(data, a.getId());
						}
					}		 
			 }
			 if(resultRef == true && resultAl == true)
			 		salvo();
			 	else
			 		erro();
		 }	 	 
	 }
	 //mapeia cardápio
	public List<RefeicaoVO> getRefeicoes() {
		
		 refeicoes = dao_ref.getRefeicoes();
		 		 
		 for(int i = 0; i < refeicoes.size(); i++) {
			RefeicaoVO r1 = refeicoes.get(i);
			
			alimentos = new ArrayList<String>();
			alimentos.add(r1.getAlimento());

			int j = i + 1;
			while( j < refeicoes.size()) {
				 RefeicaoVO r2 = refeicoes.get(j);
					 if(r1.getData().compareTo(r2.getData()) == 0) {
						alimentos.add(r2.getAlimento());
						refeicoes.remove(j);
					 } 
					 else {
						 j++; //add o indice só quando a data for diferente, pois enquanto é igual é apagado elementos e o indice permanece o mesmo
					 }	
			}

			RefeicaoVO r = new RefeicaoVO();
			 
			r.setData(r1.getData());
			r.setDataString(r1.getDataString());
			r.setDiaSemana(r1.getDiaSemana()); 
			r.setAlimentos(alimentos);
			
			cardapio.add(r);
		 }	 		 
		 		 
		 return cardapio;
	  }

	
	public String diaSemana(){ 
		 
		 String dia = "";
	       
         GregorianCalendar gc = new GregorianCalendar();
         gc.setTime(data);
         
         int diaDaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
         System.out.println(diaDaSemana);
         
         switch (diaDaSemana){
         	case 1:
         		dia = "Domingo"; break;
         	case 2:	
         		dia = "Segunda-Feira"; break;
         	case 3: 
         		dia = "Terça-Feira"; break;
         	case 4:
         		dia = "Quarta-Feira"; break;
         	case 5:
         		dia = "Quinta-Feira"; break;
         	case 6: 
         		dia = "Sexta-Feira"; break;
         	case 7: 
         		dia = "Sábado"; break;
  
         }
		 return dia; 
	 }

	public void salvo() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Refeição salva com sucesso", "!"));
	}

	public void erro() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Refeição já cadastrada."));
    }
	
	public void campoBranco() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Campo em Branco!", "Preencha os dados da refeição."));
    }
	
}


