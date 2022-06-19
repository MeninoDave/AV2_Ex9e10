package Exercicio10;

import java.sql.*;
import java.util.ArrayList;

public class SQLMetodo {
	private Factory f;
	
	//Construtor
	public SQLMetodo(Factory f)throws SQLException {
		this.f=f;
	}
	
	//Insere o valor no banco de dados
	public void insertValue(String text,String resp) {
		try {
			String sql = "INSERT INTO resultados(TEXTO,SENTIMENTO) VALUES(?,?)";
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.setString(1,text);
			ps.setString(2,resp);
			ps.execute();
			System.out.println("VALOR INSERIDO NO BD COM SUCESSO!");
			
		}catch(SQLException e) {
			System.out.println("ERRO AO INSERIR VALOR NO BANCO DE DADOS!");
		}
		
	}
	
	//Printa os valores presentes no banco de dados
	public void printValues() {
		ArrayList<Resultados>result = new ArrayList<Resultados>();
		String sql = "SELECT * FROM resultados";
		try {
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				Resultados i = new Resultados(rs.getString(1), rs.getString(2));
				result.add(i);
			}
		}catch(SQLException e) {
			System.out.println("ERRO NA LISTAGEM!");
			System.out.println(e);
		}
		
		for(Resultados r: result) {
			System.out.println(r.getResultado());
		}
	}
}
