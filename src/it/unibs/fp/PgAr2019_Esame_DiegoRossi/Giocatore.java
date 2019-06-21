package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import it.unibs.fp.mylib.InputDati;

public class Giocatore {

	private static final String RICHIESTA_NOME_GIOCATORE = "Inserisci il nome del giocatore: ";
	private int saldo;
	private String nome;
	private int posizione;
	
	public Giocatore(int saldo, String nome, int posizione) {
		super();
		this.saldo = saldo;
		this.nome = nome;
		this.posizione = posizione;
	}
	public int getPosizione() {
		return posizione;
	}
	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}
	public int getSaldo() {
		return saldo;
	}
	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Giocatore(String nome) {
		super();
		this.nome = nome;
	}
	
	
	public Giocatore(String nome, int posizione) {
		super();
		this.nome = nome;
		this.posizione = posizione;
	}
	public static Giocatore creaGiocatore() {
		String nome = InputDati.leggiStringa(RICHIESTA_NOME_GIOCATORE);
		Giocatore newGiocatore = new Giocatore(nome, 0);
		
		return newGiocatore;
	}
}
