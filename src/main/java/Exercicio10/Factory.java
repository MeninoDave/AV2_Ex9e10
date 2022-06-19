package Exercicio10;

import java.sql.*;
import javax.sql.*;
import com.mchange.v2.c3p0.*;


public class Factory {
	private  DataSource ds;

	public Factory() {
		this.ds = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Seta uma nova conexao
	public boolean setConexao(String URL) {
		ComboPooledDataSource CPDS = new ComboPooledDataSource();
		CPDS.setJdbcUrl(URL);
		CPDS.setUser("root");
		CPDS.setPassword("root");
		this.ds = CPDS;
		return returnConexao();
	}
		
	//Fabrica de conexoes do arquivo, ele ir� checar se a conex�o existe ou n�o
	//Caso d� problema, ele ir� pedir para que digite novamente o valor da URL
	public void tryConexao(String URL){
		ComboPooledDataSource CPDS = new ComboPooledDataSource();
		CPDS.setJdbcUrl(URL);
		CPDS.setUser("root");
		CPDS.setPassword("root");
		
		this.ds=CPDS;
		if(!returnConexao()) {
			System.out.println("ERRO DE CONEX�O!");
		}else {
			System.out.println(" CONEX�O COM BD REALIZADA COM SUCESSO!");
		}
	}
	
	//Retorna true ou false de acordo com o sucesso da conexao com o BD
	public boolean returnConexao() {
		try {
			this.ds.getConnection();
		}catch(SQLException e) {
			System.out.println("ERRO DE CONEX�O COM O BANCO DE DADOS!\n");
			return false;
		}
		return true;
	}
	
	public Statement getStatement()throws SQLException {
		Connection c = ds.getConnection();
		Statement ps = c.createStatement();
		return ps;
	}
	
	public Connection getConnection()throws SQLException {
		return  ds.getConnection();
	}
	
}
