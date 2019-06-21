package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import it.unibs.fp.mylib.InputDati;

public class Giocatore {

	private int saldo;
	private String nome;
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
	
	
	public static Giocatore creaGiocatore() {
		String nome = InputDati.leggiStringa("Inserisci il nome del giocatore: ");
		Giocatore newGiocatore = new Giocatore(nome);
		
		return newGiocatore;
	}
}
