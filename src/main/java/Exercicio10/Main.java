package Exercicio10;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[]args) {
		Factory f = new Factory();
		f.tryConexao("jdbc:mysql://localhost:3306/ex10");
		System.out.println("================= BEM VINDO(A)! =================");
		Scanner sc = new Scanner(System.in);
		int opt;
		try {
			SQLMetodo s = new SQLMetodo(f);
			do {
				Sistema.Menu();
				String select = sc.nextLine();
				opt =  Sistema.OptionMenu(select,3);
				if(opt==1) {
					boolean err=false;
					String t;
					do {
						System.out.println("DIGITE O TEXTO DESEJADO:");
						 t = sc.nextLine();
						err=Sistema.checkString(t);
					}while(err);
					String resp = Sistema.AnalisaString(t);
					System.out.println("Resultado= "+ resp);
					s.insertValue(t,resp);
				}else if(opt==2) {
					s.printValues();
				}
			}while(opt!=3);
		}catch (SQLException e) {System.out.println(e);}
		sc.close();
	}
}
