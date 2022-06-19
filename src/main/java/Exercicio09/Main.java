package Exercicio09;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Factory f = new Factory();
		f.tryConexao("jdbc:mysql://localhost:3306/ex09");
		Scanner sca = new Scanner(System.in);
		try {
			SQLmetodos s = new SQLmetodos(f);
			int opt;
			do {
				Menu.M1();
				String selecao = sca.nextLine();
				opt = Sistema.OptionMenu(selecao,2);
			}while(opt==-1);
			if(opt==1) {
				s.novaTabela();
			}else {
				s.existsTabela();
			}
			
			do {
				do {
					Menu.M2();
					String selecao = sca.nextLine();
					opt = Sistema.OptionMenu(selecao,5);
				}while(opt==-1);
				if(opt==1){
					if(Sistema.checkEmpty(f)) {
						System.out.println("TABELA VAZIA! FAVOR INSERIR 3 NOVOS VALORES!");
						Sistema.insertValues(f, s);
						Sistema.insertValues(f, s);
						Sistema.insertValues(f, s);
					}else {
						Sistema.insertValues(f, s);
					}
				}else if(opt==2){
					Sistema.updateValue(f,s);
					
				}else if(opt==3) {
					Sistema.deleteValue(s);
				}else if(opt==4){
					Sistema.searchValue(s);
				}
			}while(opt!=5);
			
		}catch(SQLException e) {
			System.out.println(e);
		}
	sca.close();
	}
}
