package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.sql.Date;
import java.util.List;

import com.vo.AlimentoVO;
import com.vo.RefeicaoVO;

public class DaoRefeicao {

	private static Connection c;
	private static DaoConnection dao;
	private static AlimentoVO alimento = null;
	private static RefeicaoVO refeicao =  null;
	private static List<RefeicaoVO> refeicoes = null;
	private static List<RefeicaoVO> refeicaoList = null;
	private static List<AlimentoVO> alimentosList; 

	
	private static final String INSERT_REFEICAO = "INSERT INTO refeicao (data, dia_refeicao) values(?,?)"; 
	private static final String INSERT_REFEICAO_ALIMENTO = "INSERT INTO refeicao_alimentos (data_refeicao, id_alimento) values(?,?)"; 
	private static final String SELECT_REFEICAO = "SELECT data, dia_refeicao, id_alimento FROM refeicao, refeicao_alimentos WHERE refeicao.data = refeicao_alimentos.data_refeicao and data = ?";
	
	private static final String SELECT_ALIMENTO_ID = "SELECT * FROM alimento where id = ?";
	private static final String SELECT_ALIMENTO_REFEICAO = "SELECT id_alimento FROM refeicao_alimentos WHERE data_refeicao = ?";
	private static final String SELECT_REFEICOES = "SELECT id_alimento, data, dia_refeicao FROM refeicao_alimentos, refeicao WHERE refeicao.data = refeicao_alimentos.data_refeicao";
	private static final String SELECT_REFEICOES_GROUP_BY = "SELECT descricao, id_alimento, data, dia_refeicao FROM alimento, refeicao, refeicao_alimentos WHERE refeicao.data = refeicao_alimentos.data_refeicao and alimento.id = refeicao_alimentos.id_alimento group by data, descricao";
	
	private static final String SELECT_DATA_REFEICOES = "SELECT data, dia_refeicao FROM refeicao";
 
	
	public DaoRefeicao() {
		dao = new DaoConnection(); 
		c = dao.getConnection(); 
		refeicaoList = new ArrayList<RefeicaoVO>();
		refeicoes = new ArrayList<RefeicaoVO>();
	}
	
	//SELECT_DATA_REFEICOES
	public List<RefeicaoVO> getRefeicoesData(){
		
		 try {
			 PreparedStatement stmt = c.prepareStatement(SELECT_DATA_REFEICOES);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 refeicao = new RefeicaoVO();
				 refeicao.setDiaSemana(rs.getNString("dia_refeicao"));
				 refeicao.setData(rs.getDate("data"));
				 refeicao.setDataString(dateString(refeicao.getData()));
				 refeicoes.add(refeicao);
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
		
		return refeicoes;
	}
	
	//Método que retorna id do alimento ao receber a descrição deste
	public boolean addRefeicao(Date data, String dia_refeicao) {
		 int row;
		 boolean result = false;
		 
		 java.sql.Date dataSql = new java.sql.Date(data.getTime()); 
		
		try {
			 PreparedStatement stmt = c.prepareStatement(INSERT_REFEICAO);
			 
			 stmt.setDate(1, dataSql); // stmt.setDate(1, date);
			 stmt.setNString(2, dia_refeicao);
			 
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
	
	public boolean addRefeicaoAlimento(Date data, int id) { 
		 int row;
		 boolean result = false;
		 
		 java.sql.Date dataSql = new java.sql.Date(data.getTime()); 
		
		try {
			 PreparedStatement stmt = c.prepareStatement(INSERT_REFEICAO_ALIMENTO);
			 
			 stmt.setDate(1, dataSql);
			 stmt.setInt(2, id);
			 
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
	
	public List<RefeicaoVO> getRefeicao(Date data) {//SELECT_REFEICAO
			
		 java.sql.Date dataSql = new java.sql.Date(data.getTime()); 
		 
		 try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_REFEICAO);
			 stmt.setDate(1, dataSql);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 
				refeicao = new RefeicaoVO(); 
				alimento = new AlimentoVO();
				 
				 refeicao.setData(rs.getDate("data"));
				 alimento.setId(rs.getInt("id_alimento"));
				 refeicao.setDiaSemana(rs.getNString("dia_refeicao"));

				 int id = alimento.getId();
				 
				 String descricao = transformAlimentoIdDescricao(id); //converte id de alimento em descrição
				 refeicao.setAlimento(descricao);
				 				
				refeicaoList.add(refeicao);
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
		 
		 return refeicaoList;
		
	}
	
	public boolean getAlimentoRefeicao(Date data, int id) { //SELECT_ALIMENTO_REFEICAO
		boolean result = false;
		java.sql.Date dataSql = new java.sql.Date(data.getTime()); 
		 
		 try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTO_REFEICAO);
			 stmt.setDate(1, dataSql);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 
				 System.out.println("Id: "+ rs.getInt("id_alimento"));
				 int idSql = rs.getInt("id_alimento");
				 
				 if(idSql == id) {
					 result = true;
					 break;
				 }
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
		 
		return result;
	}
	
	public  List<AlimentoVO> getAlimentoData(Date data) { //retorna lista de alimentos com id e descrição
		java.sql.Date dataSql = new java.sql.Date(data.getTime()); 
		 
		 try {
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTO_REFEICAO);
			 stmt.setDate(1, dataSql);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 AlimentoVO a;
			alimentosList = new  ArrayList<AlimentoVO>();
			
			 while (rs.next()) {
				 String descricao = " ";
				 
				 a = new AlimentoVO();
				 a.setId(rs.getInt("id_alimento"));
				 descricao = transformAlimentoIdDescricao(a.getId());
				 a.setDescricao(descricao); 
				 alimentosList.add(a);
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
		 
		return alimentosList;
	}
	
	public List<RefeicaoVO> getRefeicoes(){
		
		 try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_REFEICOES_GROUP_BY);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 
				 refeicao = new RefeicaoVO();
				 
				 refeicao.setDiaSemana(rs.getNString("dia_refeicao"));
				 refeicao.setData(rs.getDate("data"));
				 refeicao.setDataString(dateString(refeicao.getData()));
				 refeicao.setAlimento(rs.getNString("descricao"));
				 				 
				 refeicoes.add(refeicao);
				 
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
		
		return refeicoes;
	}
	
	//versão antiga de getRefeicoes
	public List<RefeicaoVO> getRefeicoess(){
		
		 try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_REFEICOES);
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 
				 refeicao = new RefeicaoVO();
				 alimento = new AlimentoVO();
				 
				 refeicao.setDiaSemana(rs.getNString("dia_refeicao"));
				 refeicao.setData(rs.getDate("data"));
				 
				 String data = dateString(refeicao.getData()); //converte date em String
			
				 refeicao.setDataString(data);
				 
				 alimento.setId(rs.getInt("id_alimento"));
									 
				 int id = alimento.getId();
				 
				 String descricao = transformAlimentoIdDescricao(id); //converte id de alimento em descrição
				 refeicao.setAlimento(descricao);
				 
				 //System.out.println("-DAO Refeicoes-\nAlimento: "+refeicao.getAlimento()+"\nDia Semana: "+refeicao.getDiaSemana()+"\nData: "+refeicao.getData()+"\n");
				 
				 refeicoes.add(refeicao);
				 
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
		
		return refeicoes;
	}
	
	public String transformAlimentoIdDescricao(int id) {
		
		String descricao = "";
				
		 try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_ALIMENTO_ID);
			 stmt.setInt(1, id);
	
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 descricao = rs.getNString("descricao");
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
		
		 return descricao;
	}
	
	public String dateString(Date date) {
		
		DateFormat strDf = new SimpleDateFormat("dd/MM/yyyy");
		String data =	strDf.format(date);
		
		return data;
	}
	
}
