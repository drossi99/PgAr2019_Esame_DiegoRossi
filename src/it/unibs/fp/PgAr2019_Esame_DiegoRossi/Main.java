package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;

public class Main {

	private static final String END = "Grazie per aver giocato!";
	private static final String NUM_GIOCATORI_NON_ACCETTATO = "Il numero dei giocatori non è accettato. RIPROVA";
	private static final String RICHIESTA_NUMERO_GIOCATORI = "Per iniziare, diteci in quanti siete? Oppure vuoi fare una partita in single player?\n\tRicorda che è possibile giocare fino a un massimo di 4 giocatori\n\t";
	private static final String FINE_RICHIESTA_NUOVA_PARTITA = "La partita è terminata. Vuoi iniziarne un'altra? (1 = Si, altro = No)\n\t";
	private static final String MSG_SCONFITTA = "\n\n\nPECCATO, NON SEI RIUSCITO A VINCERE LA PARTITA";
	private static final String MSG_VITTORIA = "****** COMPLIMENTI *****\nHAI VINTO LA PARTITA!";
	private static final String BENVENUTO = "Benvenuto in Unipoly!\nÈ un avvincente gioco per testare le tue capacità di gestione immobiliare e un po' anche la tua fortuna\n!";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(BENVENUTO);
		int numGiocatori;
		ArrayList<Giocatore> classifica = new ArrayList<Giocatore>();
		
		//si chiede il numero di giocatori, fino a un valore accettato [1-4]
		do {
			numGiocatori = InputDati.leggiIntero(RICHIESTA_NUMERO_GIOCATORI);
			if (numGiocatori < 1 || numGiocatori > 4)
				System.out.println(NUM_GIOCATORI_NON_ACCETTATO);
		} while (numGiocatori < 1 || numGiocatori > 4);

		//variabile per una eventuale nuova partita dopo la fine di quella corrente
		int scelta = 1;
		
		//finché l'utente inserisce 1 (vuole giocare), inizia una nuova partita
		do {
			int vincitore;
			
			//se la partita è a giocatore singolo viene chiamato il metodo per la partita a 1 giocatore
			if (numGiocatori == 1) {
				vincitore = Partita.newPartitaSingleplayer();
				
				//messaggi di vittoria (1) / sconfitta (0)
				if (vincitore == 1) {
					System.out.println(MSG_VITTORIA);
				} else {
					System.out.println(MSG_SCONFITTA);
				}
			}
			//ALTRIMENTI, se è multiplayer, viene chiamato il metodo partita multiplayer(argomento: numero di giocatori)
			else {
				classifica = Partita.newPartitaMultiplayer(numGiocatori);
				for (int i = 0; i < classifica.size(); i++) {
					if (i == 0) {
						System.out.println((i+1) +".\t" + classifica.get(i).getNome().toUpperCase() + "\tcon un saldo di I€€€" + classifica.get(i).getSaldo());
					}
					else System.out.println((i+1) +".\t" + classifica.get(i).getNome() + "\tcon un saldo di I€€€" + classifica.get(i).getSaldo());
				}
			}
			
			//si chiede per una nuova partita
			scelta = InputDati.leggiIntero(FINE_RICHIESTA_NUOVA_PARTITA);
		} while (scelta == 1);
		System.out.println(END);
	}

	
}
