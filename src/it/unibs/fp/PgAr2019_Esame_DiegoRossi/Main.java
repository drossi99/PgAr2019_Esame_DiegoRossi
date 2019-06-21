package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;

public class Main {

	private static final String FINE_RICHIESTA_NUOVA_PARTITA = "La partita è terminata. Vuoi iniziarne un'altra? (1 = Si, altro = No)\n\t";
	private static final String MSG_SCONFITTA = "\n\n\nPECCATO, NON SEI RIUSCITO A VINCERE LA PARTITA";
	private static final String MSG_VITTORIA = "****** COMPLIMENTI *****\nHAI VINTO LA PARTITA!";
	private static final String BENVENUTO = "Benvenuto in Unipoly!\nÈ un avvincente gioco per testare le tue capacità di gestione immobiliare e un po' anche la tua fortuna\nInizia subito creando il giocatore!";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(BENVENUTO);
		int numGiocatori;
		ArrayList<Giocatore> classifica = new ArrayList<Giocatore>();
		
		do {
			numGiocatori = InputDati.leggiIntero("Per iniziare, diteci in quanti siete? Oppure vuoi fare una partita in single player?\n\tRicorda che è possibile giocare fino a un massimo di 4 giocatori");
			if (numGiocatori < 1 || numGiocatori > 4)
				System.out.println("Il numero dei giocatori non è accettato. RIPROVA");
		} while (numGiocatori < 1 || numGiocatori > 4);

		int scelta = 1;
		do {
			int vincitore;
			if (numGiocatori == 1) {
				vincitore = Partita.newPartita();
				if (vincitore == 1) {
					System.out.println(MSG_VITTORIA);
				} else {
					System.out.println(MSG_SCONFITTA);
				}
			}
			else {
				classifica = Partita.newPartitaMultiplayer(numGiocatori);
			}
			
			scelta = InputDati.leggiIntero(FINE_RICHIESTA_NUOVA_PARTITA);
		} while (scelta == 1);
	}

	
}
