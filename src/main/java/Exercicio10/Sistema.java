package Exercicio10;

public class Sistema {
	
	public static void Menu() {
		System.out.println("====================== MENU ======================");
		System.out.println("(1) INSERIR NOVO TEXTO");
		System.out.println("(2) LISTAR TODOS OS RESULTADOS");
		System.out.println("(3) SAIR");
	}
	
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
	
	//Checa Nome e Descricao
	public static boolean checkString(String s) {
		if(s.trim().isEmpty()) {
			System.out.println(" Caractere invalido! ");
			return true;
		}else {
			return false;
		}
	}
	
	public static String AnalisaString(String text) {
		int sentiment=0; //0 == Neutro ; >0 == Divertido ; <0 == Chateado
		if(text.length()<3) {
			return "Neutro";
		}
		for(int i=0;i<text.length();i++) {
			if((text.charAt(i)==':') && (text.charAt(i+1)=='-') && (text.charAt(i+2)==')')) {
				sentiment++;
			}else if((text.charAt(i)==':') && (text.charAt(i+1)=='-') && (text.charAt(i+2)=='(')) {
				sentiment--;
			}
		}
		if(sentiment<0) {
			return "Chateado";
		}else if(sentiment==0) {
			return "Neutro";
		}else {
			return "Divertido";
		}
	}
}
