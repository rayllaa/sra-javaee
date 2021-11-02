package com.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.dao.DaoAlimento;
import com.vo.AlimentoVO;

@ManagedBean
@ViewScoped
public class AlimentoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DaoAlimento dao;
	private AlimentoVO alimento;
	private AlimentoVO alimentodb;
	private List<AlimentoVO> alimentos;
	private List<AlimentoVO> listaAlimentos;
	
	public AlimentoBean() {
		dao = new DaoAlimento();
		alimento = new AlimentoVO();
	}
	
	 public AlimentoVO getAlimento() {
	        return alimento;
	    }
	 public void setAlimento(AlimentoVO alimento) {
			this.alimento = alimento;
		}
	 public void addAlimento() {
		
		alimentodb = dao.getAlimento(alimento.getDescricao());
				
		if(alimentodb.getDescricao() == null) { //se não existe alimento com essa descrição add alimento
			
			boolean x = dao.addAlimento(alimento.getDescricao(), UsuarioBean.getSessao().getLogin());	

				if(x) {
		 			salvo();
		 			 System.out.println("Alimento salvo com sucesso!");
		 		 }		
	 	}
		 else {
			 erro();
		 	System.out.println("Alimento já cadastrado!");
		 }
	 }
	 
	 public List<AlimentoVO> getAlimentos() {
		 	
		 		alimentos = dao.getAlimentos();
		 
	        return alimentos;
	    }
	 
	 public void removeAlimento() {
		boolean result = dao.removeAlimento(alimento.getDescricao());
		
		if(result)
			deletado();
	 }
	 
//	 public boolean filtro(Object value, Object filter, Locale locale) {
//	        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
//	        if (filterText == null || filterText.equals("")) {
//	            return true;
//	        }
//	        int filterInt = getInteger(filterText);
//	 
//	        AlimentoVO a = (AlimentoVO) value;
//	        return a.getDescricao().toLowerCase().contains(filterText);
//	    }
//	 
//	 private int getInteger(String string) {
//	        try {
//	            return Integer.valueOf(string);
//	        }
//	        catch (NumberFormatException ex) {
//	            return 0;
//	        }
//	    }
	 
	  public List<AlimentoVO> getListaAlimentos() {
	        return listaAlimentos;
	    }
	 
	    public void setListaAlimentos(List<AlimentoVO> listaAlimentos) {
	        this.listaAlimentos = listaAlimentos;
	    }
	 
	 
	 public void salvo() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alimento salvo com sucesso", "!"));
	 }
	
	 public void erro() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Alimento já cadastrado."));
	  }
	 public void deletado() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Alimento deletado com sucesso", "!"));
	 }
}
