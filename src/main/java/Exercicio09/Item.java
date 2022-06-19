package Exercicio09;

public class Item {
	private int id;
	private String name;
	private String descricao;
	private float desconto;
	private float preco;
	java.sql.Date data;
	
	public Item(int i, String n, String desc, float disc, float p, java.sql.Date d) {
		this.id=i;
		this.name=n;
		this.descricao=desc;
		this.desconto=disc;
		this.preco=p;
		this.data=d;
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getItem() {
		return (this.id +" | "+ this.name +" | "+ this.descricao +" | "+this.desconto + " | " + this.preco + " | " + this.data + "\n" );
	}
	
	
	
}
