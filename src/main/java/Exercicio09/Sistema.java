package Exercicio09;

import java.util.Scanner;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Sistema {
	
	// Cria as opcoes desejadas de acordo com a quantidade de caracteres que deseja adicionar
	public static int OptionMenu(String value,int qt) {
		String check = value;
			if(!check.trim().isEmpty()) {
				if(check.length()==1 && ((int)check.charAt(0)> 48||(int)check.charAt(0)<=(48+qt))){
					return (int)check.charAt(0)-48;
				}else {
					System.out.println("Favor digitar caracteres entre '1' e "+"'"+qt+"'!");
					return -1;
				}
			}else {
					System.out.println("CARACTERE NÃO VALIDO!");
					return -1;
			}
		
	}
	
	//====================================================================================================================================================================================
	//Os proximos metodos retornam true se encontrarem algum erro
	//Tem que ser chato nesses metodos para nao gerarem ParseException
	
	//Checa Nome e Descricao
	private static boolean checkString(String s) {
		if(s.trim().isEmpty()) {
			System.out.println(" Caractere invalido! ");
			return true;
		}else {
			return false;
		}
	}
	
	//Checa int
	private static boolean checkInt(String i) {
		if(i.trim().isEmpty()) {
			System.out.println(" Caractere invalido! ");
			return true;
		}else {
			for(int j=0;j<i.length();j++) {
				if(((int)i.charAt(j)<48 || (int)i.charAt(j)>57)){
					System.out.println("Apenas números são validos!");
					return true;
				}
			}
			return false;
		}
	}
	
	//Checa float
	private static boolean checkFloat(String f) {
		if(f.trim().isEmpty()) {
			System.out.println(" Caractere invalido! ");
			return true;
		}else {
			for(int j=0;j<f.length();j++) {
				if(((int)f.charAt(j)<48 || (int)f.charAt(j)>57)){
					if(f.charAt(j)!='.') {
						System.out.println("Apenas números e '.' são validos!");
						return true;
					}
					
				}
			}
			return false;
		}
	}
	
	//Checa Data
	private static boolean checkData(String d) {
		if(d.trim().isEmpty()) {
			System.out.println(" Caractere invalido! ");
		}else if(d.length()!=10) {
			System.out.println(" String invalida! ");
			return true;
		}
		for(int i=0;i<d.length();i++) {
			if(((int)d.charAt(i)<48 && (int)d.charAt(i)>57)&&((d.charAt(4)!='-')&&(d.charAt(7)!='-'))) {
				System.out.println("Apenas números são validos!");
				return true;
			}
		}
		int ano = ((int)d.charAt(0)-48)*1000 + ((int)d.charAt(1)-48)*100 + ((int)d.charAt(2)-48)*10 + ((int)d.charAt(3)-48);
		int mes = ((int)d.charAt(5)-48)*10 + ((int)d.charAt(6)-48);
		int dia = ((int)d.charAt(8)-48)*10 + ((int)d.charAt(9)-48);
		
		if((dia>31 && dia<1)&&(mes>12 && mes<1)&&(ano>9999 && mes<1000)) {
			System.out.println("Apenas valido no seguinte metodo: AAAA-MM-DD ");
			return true;
		}
		return false;
	}
	//====================================================================================================================================================================================
	//printa na tela todos os valores existentes na tabela; 
	public static void printaResult(ArrayList<Item> i) {
		for(Item item: i) {
			System.out.println(item.getItem());
		}
	}
	
	//checa se a tabela esta ou nao vazia (VAZIA == TRUE)
	public static boolean checkEmpty(Factory f) {
		try {
			SQLmetodos s = new SQLmetodos(f);
			if(s.isEmpty()==1){
				return false;
			}else {
				return true;
			}
 			
		}catch(SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
	//Obtem os valores do teclado, converte-os e lanca no SQL
	public static void insertValues(Factory f, SQLmetodos s) {
		String line="";
		Scanner sc = new Scanner(System.in);
		boolean err=false;
		
		do {
			System.out.println("DIGITE O ID DO ITEM:");
			line = sc.nextLine();
			err=checkInt(line);
		}while(err);
		int id = Integer.parseInt(line);
		
		do {
			System.out.println("DIGITE O NOME DO ITEM:");
			line = sc.nextLine();
			err=checkString(line);
		}while(err);
		String nome = line;
		
		do {
			System.out.println("DIGITE A DESCRICAO DO ITEM:");
			line = sc.nextLine();
			err=checkString(line);
		}while(err);
		String descricao = line;
		
		do {
			System.out.println("DIGITE O DESCONTO DO ITEM:");
			line = sc.nextLine();
			err=checkFloat(line);
		}while(err);
		float discount = Float.parseFloat(line);
		
		do {
			System.out.println("DIGITE O PREÇO DO ITEM:");
			line = sc.nextLine();
			err=checkFloat(line);
		}while(err);
		float price = Float.parseFloat(line);
		
		do {
			System.out.println("DIGITE A DATA:");
			line = sc.nextLine();
			err=checkData(line);
		}while(err);
		try {
			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(line);
			java.sql.Date date =  new java.sql.Date(utilDate.getTime());
			s.insertTabela(id, nome, descricao, discount, price, date);
		}catch (ParseException e) {System.out.println("ERRO AO CONVERTER A DATA PARA 'java.sql.Date'");}
	}
	
	//checa se o valor da id existe e atualiza o item, se nao existir, ira pedir para criar uma id
	public static void updateValue(Factory f, SQLmetodos s) {
		Scanner sc = new Scanner(System.in);
		boolean err=false;
		String line;
		do {
			System.out.println("DIGITE O ID DO ITEM:");
			line = sc.nextLine();
			err=checkInt(line);
		}while(err);
		int id = Integer.parseInt(line);
		int existe = s.isIDEmpty(id);
		if(existe==1){
			do {
				System.out.println("DIGITE O NOVO NOME:");
				line = sc.nextLine();
				err=checkString(line);
			}while(err);
			String nome = line;
			
			do {
				System.out.println("DIGITE A NOVA DESCRICAO:");
				line = sc.nextLine();
				err=checkString(line);
			}while(err);
			String descricao = line;
			
			do {
				System.out.println("DIGITE O NOVO DESCONTO:");
				line = sc.nextLine();
				err=checkFloat(line);
			}while(err);
			float discount = Float.parseFloat(line);
			
			do {
				System.out.println("DIGITE O NOVO PREÇO DO ITEM:");
				line = sc.nextLine();
				err=checkFloat(line);
			}while(err);
			float price = Float.parseFloat(line);
			
			do {
				System.out.println("DIGITE A NOVA DATA:");
				line = sc.nextLine();
				err=checkData(line);
			}while(err);
			try {
				java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(line);
				java.sql.Date date =  new java.sql.Date(utilDate.getTime());
				s.updateTabela(id, nome, descricao, discount, price, date);
			}catch (ParseException e) {System.out.println("ERRO AO CONVERTER A DATA PARA 'java.sql.Date'");}
		}else if(existe==0) {
			System.out.println("ID NAO EXISTE! CRIANDO UM NOVO ITEM...");
			insertValues(f,s);
		}
	}
	
	public static void deleteValue(SQLmetodos s) {
		Scanner sc = new Scanner(System.in);
		boolean err=false;
		String line;
		do {
			System.out.println("DIGITE O ID DO ITEM:");
			line = sc.nextLine();
			err=checkInt(line);
		}while(err);
		int id = Integer.parseInt(line);
		s.deleteFromTabela(id);
	}
	
	//procura pelos valores chave e os imprime
	public static void searchValue(SQLmetodos s) {
		Scanner sc = new Scanner(System.in);
		boolean err=false;
		String line;
		do {
			System.out.println("DIGITE O NOME A SER PESQUISADO:");
			line = sc.nextLine();
			err=checkString(line);
		}while(err);
		String[]substr = line.split(" ");
		int result=0;
		for(String st: substr) {
			result+=s.existsName(st);
		}
		if(result==0) {
			System.out.println("PALAVRA-CHAVE NAO ENCONTRADA!");
		}else {
			for(String st: substr) {
				s.FilterResults(st);
			}
		}
		
	}
	
}
