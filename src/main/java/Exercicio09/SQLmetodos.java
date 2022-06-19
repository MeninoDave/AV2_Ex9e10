package Exercicio09;

import java.sql.*;
import java.util.ArrayList;

public class SQLmetodos{
	private Statement stm;
	private Factory f;
	
	//Construtor
	public SQLmetodos(Factory f)throws SQLException {
		this.stm =f.getStatement();
		this.f=f;
	}
	
	//Checa a existencia da tabela
	public void existsTabela() {
		try {
			stm.execute("CREATE TABLE produtos (ID int,NOME varchar(100),DESCRICAO varchar(200),DESCONTO float, PRECO float, DATA date, PRIMARY KEY(ID))");
		}catch (SQLException e) {
			String msg = e.getMessage();
			if(!msg.equals("Table 'produtos' already exists")) {
				System.out.println("TABELA NAO EXISTENTE! CRIANDO UMA NOVA...");
			}
		}
	}
		
		
	
	
	//Cria uma nova tabela, se ela ja existir, envia mensagem de erro
	public void novaTabela() {
		try {
			stm.execute("CREATE TABLE produtos (ID int,NOME varchar(100),DESCRICAO varchar(200),DESCONTO float, PRECO float, DATA date, PRIMARY KEY(ID))");
		}catch (SQLException e) {
			String msg = e.getMessage();
			if(msg.equals("Table 'produtos' already exists")) {
				System.out.println("TABELA JA EXISTE!! \n ");
			}
		}
	}
	
	
	//insere um novo elemento na tabela
	public void insertTabela(int ID, String nome, String descricao, float desconto,float preco, java.sql.Date data){
		try {
			String sql = "INSERT INTO produtos(ID,NOME,DESCRICAO,DESCONTO,PRECO,DATA) VALUES (?,?,?,?,?,?)";
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.setInt(1, ID);
			ps.setString(2, nome);
			ps.setString(3, descricao);
			ps.setFloat(4, desconto);
			ps.setFloat(5, preco);
			ps.setDate(6, data);
			
			ps.execute();
			System.out.println("ITEM INSERIDO COM SUCESSO!");
		}catch(SQLException e) {
			System.out.println("ERRO AO INSERIR O OBJETO"+nome);
		}
	}
	
	//atualiza a tabela de acordo com o valor
	public void updateTabela(int ID, String nome, String descricao, float desconto,float preco, java.sql.Date data) {
		String sql = "UPDATE produtos SET NOME=(?),DESCRICAO=(?),DESCONTO=(?),PRECO=(?),DATA=(?) WHERE ID = "+ID;
		try {
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, descricao);
			ps.setFloat(3, desconto);
			ps.setFloat(4, preco);
			ps.setDate(5, data);
			ps.execute();
		}catch(SQLException e) {
			System.out.println("ERRO AO ATUALIZAR VALOR NO BANCO DE DADOS! ");
			System.out.println(e.getMessage());
		}
	}
	
	
	//obtem os resultados presentes na tabela
	public ArrayList<Item> getResults() {
		ArrayList<Item>itens = new ArrayList<Item>();
		String sql = "SELECT * FROM produtos";
		try {
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				Item i = new Item(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getFloat(5),rs.getDate(6));
				itens.add(i);
			}
		}catch(SQLException e) {
			System.out.println("ERRO NA LISTAGEM!");
			System.out.println(e);
		}
		
		return itens;
		
	}
	
	//Filtra a lista com base na palavra-chave
	public void FilterResults(String keyword){
		ArrayList<Item>itens = new ArrayList<Item>();
		String sql = "SELECT * FROM produtos WHERE NOME= (?)";
		try {
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.setString(1, keyword);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				Item i = new Item(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getFloat(5),rs.getDate(6));
				itens.add(i);
			}
		}catch(SQLException e) {
			System.out.println("ERRO NA LISTAGEM!");
			System.out.println(e);
		}
		
		Sistema.printaResult(itens);
	}
	
	//deleta um item da tabela
	public void deleteFromTabela(int id) {
		//primeiro, checa se o valor de id existe
		ArrayList<Item> search = getResults();
		boolean exists = false;
		for(Item i:search) {
			if(i.getID() == id) {
				exists = true;
			}
		}
		if(exists==true) {
			//depois, realiza a query (se a id existir)
			String sql = "DELETE FROM produtos WHERE ID = "+id;
			try {
				PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
				ps.execute();
				System.out.println("ITEM DELETADO COM SUCESSO! ");
			}catch(SQLException e) {
				System.out.println("ERRO AO DELETAR NO BANCO DE DADOS! ");
				System.out.println(e.getMessage());
			}
		}else {
			System.out.println("ERRO! ID NÃO EXISTENTE NO BANCO DE DADOS! ");
		}
	}
	
	//checa se o nome existe ou não na tabela
		public int existsName(String name) {
			try {
				String sql = "SELECT EXISTS(SELECT 1 FROM produtos WHERE NOME = (?));";
				PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
				ps.setString(1, name);
				ps.execute();
				ResultSet rs = ps.getResultSet();
				int result=0;
				while(rs.next()) {
					if(rs.getInt(1)==1) {
						result = 1;
					}else {
						result = 0;
					}
				}
				return result;
				
			}catch(SQLException e) {System.out.println("ERRO AO CHECAR QTDE DE ITENS NA TABELA"); return -1;}
			 
		}
	
	//checa se a tabela esta ou nao vazia
	public int isEmpty() {
		try {
			String sql = "SELECT EXISTS(SELECT 1 FROM produtos);";
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			int result=0;
			while(rs.next()) {
				if(rs.getInt(1)==1) {
					result = 1;
				}else {
					result = 0;
				}
			}
			return result;
			
		}catch(SQLException e) {System.out.println("ERRO AO CHECAR QTDE DE ITENS NA TABELA"); return -1;}
		 
	}
	
	//Checa se a ID existe no banco de dados 
	public int isIDEmpty(int id) {
		try {
			String sql = "SELECT EXISTS(SELECT 1 FROM produtos WHERE ID = (?));";
			PreparedStatement ps = this.f.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			int result=0;
			while(rs.next()) {
				if(rs.getInt(1)==1) {
					result = 1;
				}else {
					result = 0;
				}
			}
			return result;
			
		}catch(SQLException e) {System.out.println("ERRO AO CHECAR A EXISTENCIA DO ITEM NA TABELA"); return -1;}
		 
	}
	
		
			
}
