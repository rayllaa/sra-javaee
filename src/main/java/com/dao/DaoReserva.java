package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.vo.AlimentoVO;
import com.vo.RefeicaoVO;
import com.vo.ReservaVO;

public class DaoReserva {
	private static Connection c;
	private static DaoAlimento dao_ali;
	private static DaoConnection dao;
	private DaoRefeicao dao_ref;
																	                //data_refeicao, data_reserva, id_alimento, login, status 
	private static final String INSERT_RESERVA_ALIMENTO = "INSERT INTO reserva (data_refeicao, data_reserva, id_alimento, login_aluno, status) values(?, ?, ?, ?, ?)"; 
	private static final String SELECT_RESERVA_ALIMENTO = "SELECT * FROM reserva WHERE data_refeicao = ? AND id_alimento = ? AND login_aluno = ?"; 
	private static final String SELECT_RESERVAS_ALIMENTOS = "SELECT * FROM reserva where data_refeicao = ? and id_alimento = ?"; 
	private static final String SELECT_RESERVAS_REFEICAO = "SELECT * FROM reserva where data_refeicao = ?"; 
	private static final String SELECT_RESERVAS_ALUNOS = "SELECT * FROM reserva order by data_refeicao, login_aluno";
	private static AlimentoVO alimento = null;
	private static RefeicaoVO refeicao =  null;
	private static ReservaVO reserva =  null; 
	private static ArrayList<ReservaVO> reservas = null;

	// Pegar quantidade de reservas de uma determinada data e alimento 
	// Salvar reserva data, id alimento, id aluno 
	//get reserva data, id alimento, id aluno (associação 3 tabelas)
	// cancelar reserva -  apagar reserva de um determinado aluno 
	
	public DaoReserva() {
		
		dao = new DaoConnection(); 
		c = dao.getConnection(); 
		dao_ali = new DaoAlimento();
		dao_ref = new DaoRefeicao();
		reservas = new ArrayList<ReservaVO>();

	}
	
	public boolean addReserva(Date data, String descricao, String login) {
		
		 int row;
		 boolean result = false;
		 Date date = new Date();
		 java.sql.Date datareservasql = new java.sql.Date(date.getTime()); 
		 java.sql.Date datarefeicaoSql = new java.sql.Date(data.getTime()); 
		 int id_alimento = dao_ali.transformAlimentoDescricaoId(descricao);
				 
		try {
			 PreparedStatement stmt = c.prepareStatement(INSERT_RESERVA_ALIMENTO);
			 
			 stmt.setDate(1, datarefeicaoSql); 
			 stmt.setDate(2, datareservasql); 
			 stmt.setInt(3, id_alimento);
			 stmt.setNString(4, login);
			 stmt.setBoolean(5, true);

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
	
	public boolean existenciaReservaAluno(Date data, String descricao, String login) { //SELECT_RESERVA_ALIMENTO
		 boolean result = false;
		 
		 java.sql.Date datarefeicaoSql = new java.sql.Date(data.getTime()); 
		 int id_alimento = dao_ali.transformAlimentoDescricaoId(descricao);
		
		try {
			 PreparedStatement stmt = c.prepareStatement(SELECT_RESERVA_ALIMENTO);
			 
			 stmt.setDate(1, datarefeicaoSql); 
			 stmt.setInt(2, id_alimento);
			 stmt.setNString(3, login);

			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 result = true;
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
	
	public int reservaAlimento(Date data, String descricao) { //SELECT_RESERVAS_ALIMENTOS
		
		 int quantidade = 0;
		 java.sql.Date datarefeicaoSql = new java.sql.Date(data.getTime()); 
		 int id_alimento = dao_ali.transformAlimentoDescricaoId(descricao);
		
		try {
			 PreparedStatement stmt = c.prepareStatement(SELECT_RESERVAS_ALIMENTOS);
			 
			 stmt.setDate(1, datarefeicaoSql); 
			 stmt.setInt(2, id_alimento);

			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 quantidade++; //quantidade de reservas de um determinado alimento
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
		
		return quantidade;
	}

	//SELECT_RESERVAS_REFEICAO
	public int reservaRefeicao(Date data) { 
		
		 int reservas = 0;
		 java.sql.Date datarefeicaoSql = new java.sql.Date(data.getTime()); 
		
		try {
			 PreparedStatement stmt = c.prepareStatement(SELECT_RESERVAS_REFEICAO);
			 stmt.setDate(1, datarefeicaoSql); 

			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 reservas++;	 
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
		
		return reservas;
	}

	//SELECT_RESERVAS_ALUNOS
	
	public ArrayList<ReservaVO> reservasAlunos() {

		try {
			 //Execute SQL query
			 PreparedStatement stmt = c.prepareStatement(SELECT_RESERVAS_ALUNOS); //reservas ordenadas por data e alunos
			 
			 ResultSet rs = stmt.executeQuery();
			 
			 while (rs.next()) {
				 
				 refeicao = new RefeicaoVO();
				 alimento = new AlimentoVO();
				 reserva = new ReservaVO();
				 
				 refeicao.setData(rs.getDate("data_refeicao"));
				 reserva.setDataReserva(rs.getDate("data_reserva"));
				 reserva.setLogin_aluno(rs.getNString("login_aluno"));
				 alimento.setId(rs.getInt("id_alimento"));

				 String dataRefeicao = dao_ref.dateString(refeicao.getData()); //converte date em String
				 String dataReserva = dao_ref.dateString(reserva.getDataReserva());
			
				 reserva.setDataRefeicaoString(dataRefeicao);
				 reserva.setDataReservaString(dataReserva);
				 									 
				 int id = alimento.getId();
				 
				 String descricao = dao_ref.transformAlimentoIdDescricao(id); //converte id de alimento em descrição
				 reserva.setDescricaoAlimento(descricao);
				 
				 reservas.add(reserva);			 
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
		
		return reservas;
		
	}
	
}

