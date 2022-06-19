package Exercicio10;

public class Resultados {
	private String texto;
	private String sentimento;
	
	public Resultados(String texto, String sentimento) {
		this.texto=texto;
		this.sentimento=sentimento;
	}
	
	public String getResultado() {
		return(this.texto + " | " + this.sentimento + "\n");
	}
}
