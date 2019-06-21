package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

public class Casella {
	/**
	 * CLASSE Casella
	 * 5 attributi ID, NOME, TIPO, VALORE DEL SALDO CHE MODIFICA, LISTA MESSAGGI (ultimi 2 per probabilità/imprevisti)
	 */
	private int id;
	private String name;
	private String type;
	private int amount;
	private String listaMessaggi;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getListaMessaggi() {
		return listaMessaggi;
	}
	
	public void setListaMessaggi(String listaMessaggi) {
		this.listaMessaggi = listaMessaggi;
	}
	
	public Casella(int id, String name, String type, int amount, String listaMessaggi) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.amount = amount;
		this.listaMessaggi = listaMessaggi;
	}
	public Casella() {
		super();
	}
	
}
