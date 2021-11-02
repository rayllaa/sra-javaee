package com.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.vo.*;
import com.dao.*;

//Separar vo da lógica
@ManagedBean
@ViewScoped
@SessionScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private DaoUsuario dao;
	private UsuarioVO usuario;
	private AlunoVO aluno;
	private AdministradorVO adm;
	private List<AlunoVO> alunos;
	private static UsuarioVO usuariodb;
	private static FacesContext fc; 
	private static HttpSession session;
	private List<AlunoVO> alunosList;

	public UsuarioBean() {
		dao = new DaoUsuario();
		usuario = new UsuarioVO();
		aluno = new AlunoVO();
		adm = new AdministradorVO();
		alunos = new ArrayList<AlunoVO>();
	}		
	
	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}
	
	public AlunoVO getAluno() {
		return aluno;
	}

	public void setAluno(AlunoVO aluno) {
		this.aluno = aluno;
	}

	public AdministradorVO getAdm() {
		return adm;
	}

	public void setAdm(AdministradorVO adm) {
		this.adm = adm;
	}
	
	public List<AlunoVO> getAlunosList() {
		return alunosList;
	}

	public void setAlunosList(List<AlunoVO> alunosList) {
		this.alunosList = alunosList;
	}
	
	public static UsuarioVO getSessao() { //pegar sessao de outra forma
		
		UsuarioVO user = (UsuarioVO)session.getAttribute("usuario");	

		return user;
	}
	
	public String validacao() {
		String pag = "";
		 usuariodb = dao.validaUsuario(usuario.getLogin(), usuario.getSenha());
		
		if(usuariodb.getLogin() == null) {
			erroLogin();
			System.out.println("Esse cadastro não existe!");
		}
		else if(usuariodb.getLogin().equals(usuario.getLogin()) && usuariodb.getSenha().equals(usuario.getSenha())) {
				
					System.out.println("VO: "+usuariodb.getNome());
					
					if(usuariodb.getLogin().equals("adm") && usuariodb.getSenha().equals("adm"))  //teste temporário login de adm estático
						pag = "adm_inicio.xhtml?faces-redirect=true";
					else
						pag = "aluno_inicio.xhtml?faces-redirect=true";
					
			//coloca login na sessão		
			fc = FacesContext.getCurrentInstance();
			session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("usuario", usuariodb);		

		} 
		else {
			erroLogin();
			System.out.println("Dados incorretos");
			
		}
		
		return pag;
	}

	public String finalizeSession() {
	    session.invalidate(); 
	    
	    System.out.println(usuariodb.getLogin());
	   // usuariodb = null;
	    
	     System.out.println("finalizeSessiom");
	     

	return "login.xhtml";

	}

	public void addUsuario() {
		
		//testar se existe usuario com esses dados antes --> login, cpf, rg e prontuario antes de fazer o cadastro
//		UsuarioVO usuario = new UsuarioVO(login, senha, nome, email, cpf, rg, celular);
//		AlunoVO a = dao.existenciaAluno(usuario);
	
		boolean r = dao.addUsuario(usuario.getLogin(), usuario.getSenha(), usuario.getNome(), usuario.getEmail(), usuario.getCpf(), usuario.getRg(), usuario.getCelular()); //senha, nome, email, cpf, rg, celular
		
		System.out.println("Usuario cadastrado"+r);
		System.out.println(aluno.getProntuario());
		if(r) {
			r = dao.addAluno(aluno.getProntuario(), usuario.getLogin());
			
			if(r) {
				sucessoCadastro();
				System.out.println("Cadastro realizado com sucesso!");
			}
			else {
				error();
				System.out.println("Cadastro não realizado!");
			}
		
		}	
		else {
			cadastroExiste();
			System.out.println("Cadastro não realizado!");
		}
	}
	
	
	public List<AlunoVO> listaAlunos() {
		alunos = dao.getAlunos();
		
		return alunos;
	}
	
	 public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
	        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
	        if (filterText == null || filterText.equals("")) {
	            return true;
	        }
	        int filterInt = getInteger(filterText);
	 
	        AlunoVO user = (AlunoVO) value;
	        return user.getLogin().toLowerCase().contains(filterText);
	    }
	 
	    private int getInteger(String string) {
	        try {
	            return Integer.valueOf(string);
	        }
	        catch (NumberFormatException ex) {
	            return 0;
	        }
	    }
	
	 public void sucessoCadastro() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso", "!"));
	 }
	
	 public void erroLogin() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Dados incorretos."));
	    }

	 public void error() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Usuário não Cadastrado."));
	    }
	 public void cadastroExiste() {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Cadastro já existe"));
	    }
}
