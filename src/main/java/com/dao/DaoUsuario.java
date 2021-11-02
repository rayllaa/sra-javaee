package com.dao;

import java.sql.Connection; //import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.vo.*;

public class DaoUsuario {
	
	private static final String SELECT_ALUNO = "SELECT prontuario FROM aluno where login_aluno = ?";
	private static final String SELECT_USUARIO = "SELECT * FROM usuario where login = ?";
	//private static final String SELECT_CADASTRO = "SELECT login, cpf, rg, prontuario FROM usuario, aluno"; //testa login amarrado com prontuario
	private static final String INSERT_USUARIO = "INSERT INTO usuario (login, senha, nome, telefone, cpf, rg, email) values (?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_ALUNO = "INSERT INTO aluno (prontuario, login_aluno) values (?, ?)";
	private static final String SELECT_ALUNOS = "SELECT prontuario, login_aluno, nome, email, telefone, cpf, rg FROM aluno, usuario WHERE aluno.login_aluno = usuario.login order by login_aluno";	

	private static Connection c;
	private DaoConnection dao;
	private static UsuarioVO us = null;
	private static AlunoVO a = null;
	private static ArrayList<AlunoVO> alunos;



	public DaoUsuario() {
		dao = new DaoConnection(); //cria conexão
		c = dao.getConnection(); //recebe conexão
	}
	
	
	public UsuarioVO validaUsuario(String login, String senha) {
		
		us = new UsuarioVO();
		
		try {
			 PreparedStatement stmt = c.prepareStatement(SELECT_USUARIO);
			 stmt.setNString(1, login);
			// stmt.setInt(parameterIndex, value);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 us.setLogin(rs.getString("login"));
				 us.setSenha(rs.getString("senha"));
				 us.setNome(rs.getNString("nome"));
				 us.setCpf(rs.getNString("cpf"));
				 us.setRg(rs.getNString("rg"));
				 us.setEmail(rs.getNString("email"));
				 us.setCelular(rs.getNString("telefone"));
		     }
			 rs.close();
			 stmt.close();
			 
	    } catch (SQLException se) {
			System.out.println("Exception: SQLException");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: Exception");
			e.printStackTrace();
		}
		
		return us;
		
	}
	
	public UsuarioVO getAluno(String login) {
		 us = new UsuarioVO();
		 a = new AlunoVO();
		
		try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALUNO);
			 stmt.setNString(1, login);
			// stmt.setInt(parameterIndex, value);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 a.setNome(rs.getString("nome"));
				 a.setRg(rs.getString("rg"));
				 a.setCpf(rs.getString("cpf"));
				 a.setProntuario(rs.getString("prontuario"));
				 a.setCelular(rs.getString("celular"));
				 a.setEmail(rs.getString("email"));
		     }
			 rs.close();
			 stmt.close();
			 
	    } catch (SQLException se) {
			System.out.println("Exception: SQLException");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: Exception");
			e.printStackTrace();
		}
		
		return us;
	}
	
	public boolean addUsuario(String login, String senha, String nome, String email, String cpf, String rg, String celular) {
		 int row;
		 boolean result = false;
		 
		try {
			 PreparedStatement stmt = c.prepareStatement(INSERT_USUARIO);
			 stmt.setNString(1, login);
			 stmt.setNString(2, senha);
			 stmt.setNString(3, nome);
			 stmt.setNString(4, celular);
			 stmt.setNString(5, cpf);
			 stmt.setNString(6, rg);
			 stmt.setNString(7, email);

			 row = stmt.executeUpdate();//se add retorna false
			 stmt.close();
			 
			 if(row == 1) 
				 result = true;
			 
	    } catch (SQLException se) {
			System.out.println("Exception: SQLException");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: Exception");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean addAluno(String prontuario, String login) {
		 int row;
		 boolean result = false;
		
		try {
			 PreparedStatement stmt = c.prepareStatement(INSERT_ALUNO);
			 stmt.setNString(1, prontuario);
			 stmt.setNString(2, login);
			
			 row = stmt.executeUpdate();
			 stmt.close();
			 
			 if(row == 1) 
				 result = true;
			 
	    } catch (SQLException se) {
			System.out.println("Exception: SQLException");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: Exception");
			e.printStackTrace();
		}
		
		return result;
	}
	
	//testar cada campos que não podem repetir
		public UsuarioVO existenciaAluno(UsuarioVO user, AlunoVO a) {
			 us = new UsuarioVO();
			 a = new AlunoVO();
			
			try {
				 //Execute SQL query
				 PreparedStatement stmt = c.prepareStatement(SELECT_ALUNO);
				 stmt.setNString(1, us.getLogin());
				 stmt.setNString(2, us.getSenha());
				 stmt.setNString(3, us.getNome());
				 stmt.setNString(4, us.getCelular());
				 stmt.setNString(5, us.getCpf());
				 stmt.setNString(6, us.getRg());
				 stmt.setNString(7, us.getEmail());
				// stmt.setInt(parameterIndex, value);
				 
				 ResultSet rs = stmt.executeQuery();
				 
				 while (rs.next()) {
					 a.setNome(rs.getString("nome"));
					 a.setRg(rs.getString("rg"));
					 a.setCpf(rs.getString("cpf"));
					 a.setProntuario(rs.getString("prontuario"));
					 a.setCelular(rs.getString("telefone"));
					 a.setEmail(rs.getString("email"));
			     }
				 rs.close();
				 stmt.close();
				 
		    } catch (SQLException se) {
				System.out.println("Exception: SQLException");
				se.printStackTrace();
			} catch (Exception e) {
				System.out.println("Exception: Exception");
				e.printStackTrace();
			}
			
			return us;
		}
		
		public ArrayList<AlunoVO> getAlunos() {
			
			alunos = new ArrayList<AlunoVO>();
			
			try {
				PreparedStatement stmt = c.prepareStatement(SELECT_ALUNOS);
				 
				 ResultSet rs = stmt.executeQuery();
				 				 
				 while (rs.next()) {
					 a = new AlunoVO();
					 
					 a.setProntuario(rs.getString("prontuario"));
					 a.setLogin(rs.getNString("login_aluno"));
					 a.setNome(rs.getString("nome"));
					 a.setRg(rs.getString("rg"));
					 a.setCpf(rs.getString("cpf"));
					 a.setCelular(rs.getString("telefone"));
					 a.setEmail(rs.getString("email"));
					 
					 alunos.add(a);					 
			     }
				 
				 rs.close();
				 stmt.close();	 
				 
		    } catch (SQLException se) {
				System.out.println("Exception: SQLException");
				se.printStackTrace();
			} catch (Exception e) {
				System.out.println("Exception: Exception");
				e.printStackTrace();
			}
			
			return alunos;
		}
}
