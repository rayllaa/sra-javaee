package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vo.AlimentoVO;

public class DaoAlimento {

	private static Connection c;
	private static DaoConnection dao;
	private static AlimentoVO a = null;
	private static ArrayList<AlimentoVO> alimentos = null;
	private static List<String> lista = null;
	
	private static final String INSERT_ALIMENTO = "INSERT INTO alimento (descricao, login_adm) values(?,?)";
	private static final String SELECT_ALIMENTO = "SELECT * FROM alimento WHERE descricao = ?"; //consulta com apenas parte do nome
	private static final String SELECT_ALIMENTOS = "SELECT * FROM alimento order by descricao";
	private static final String DELETE_ALIMENTO = "DELETE FROM alimento WHERE id = ?";
	private static final String SELECT_ALIMENTO_DESCRICAO = "SELECT * FROM alimento where descricao = ?";




	public DaoAlimento() {
		dao = new DaoConnection(); 
		c = dao.getConnection(); 
	}
	
	public boolean addAlimento(String descricao, String login) {
		 int row;
		 boolean result = false;
		
		try {
			 PreparedStatement stmt = c.prepareStatement(INSERT_ALIMENTO);
			 stmt.setNString(1, descricao);
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
	
	 public AlimentoVO getAlimento(String descricao) { 
		 
		 try { 
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTO);
			 stmt.setNString(1, descricao);
		 
			 ResultSet rs = stmt.executeQuery();
		 
			 a = new AlimentoVO();
			 
			 while (rs.next()) { //não precisa do while, apenas 1 registro
				 a.setId(rs.getInt("id"));
				 a.setDescricao(rs.getString("descricao")); 
			} 
			 rs.close(); 
			 stmt.close();
		 } 
		 catch (SQLException se) { 
			 System.out.println("Exception: SQLException");
			 se.printStackTrace(); 
		 } 
		 catch (Exception e) {
			 System.out.println("Exception: Exception"); 
			 e.printStackTrace(); 
		}
	 
	    return a; 
	 }
	
	 public List<AlimentoVO> getAlimentos() { 
		 
		 alimentos = new ArrayList<AlimentoVO>();
			
		 try { 
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTOS);
		 
			 ResultSet rs = stmt.executeQuery();
		 
			 while (rs.next()) {
					 a = new AlimentoVO();
					 a.setId(rs.getInt("id"));
					 a.setDescricao(rs.getString("descricao")); 
					
					 alimentos.add(a);  //add no array
			} 
			 rs.close(); 
			 stmt.close();
		 } 
		 catch (SQLException se) { 
			 System.out.println("Exception: SQLException");
			 se.printStackTrace(); 
		 } 
		 catch (Exception e) {
			 System.out.println("Exception: Exception"); 
			 e.printStackTrace(); 
		}
	 
	    return alimentos; 
	 }
	 
 public List<String> listaAlimentos() { 
		 
		 lista = new ArrayList<String>();
			
		 try { 
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTOS);
		 
			 ResultSet rs = stmt.executeQuery();
		 
			 while (rs.next()) {
					 a = new AlimentoVO();
					 a.setId(rs.getInt("id"));
					 a.setDescricao(rs.getString("descricao")); 
					
					 lista.add(a.getDescricao());  //add no array
			} 
			 rs.close(); 
			 stmt.close();
		 } 
		 catch (SQLException se) { 
			 System.out.println("Exception: SQLException");
			 se.printStackTrace(); 
		 } 
		 catch (Exception e) {
			 System.out.println("Exception: Exception"); 
			 e.printStackTrace(); 
		}
	 
	    return lista; 
	 }
	 
	 public boolean removeAlimento(String descricao) { 
		 int row = 0;
		 boolean result = false;
		 int id;
		 
		 a = getAlimento(descricao);
		 id = a.getId();
		
		 try { 
			 PreparedStatement stmt = c.prepareStatement(DELETE_ALIMENTO);
			 stmt.setInt(1, id);
			
			row = stmt.executeUpdate(); // retorna se alguma qtd de linhas que foram alteradas
			stmt.close();// execute equivalente ao executeUpdate -> retorna false quando poditivo(inserir, deletar, atualizar)
			
			 if(row == 1) 
				 result = true;
		 } 
		 catch (SQLException se) { 
			 System.out.println("Exception: SQLException");
			 se.printStackTrace(); 
		 } 
		 catch (Exception e) {
			 System.out.println("Exception: Exception"); 
			 e.printStackTrace(); 
		
		 }
		 
		 return result;
		 
	 }
	 
	 public int transformAlimentoDescricaoId(String descricao) {
			
			int id = 0;
			
			 try {
				 //Execute SQL query
				 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTO_DESCRICAO);
				 stmt.setNString(1, descricao);
		
				 ResultSet rs = stmt.executeQuery();
				 
				 while (rs.next()) {
					 id = rs.getInt("id");
					 
					 System.out.println("Id: "+id+"\nDescrição:"+descricao);
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
			
			 return id;
		}
}
