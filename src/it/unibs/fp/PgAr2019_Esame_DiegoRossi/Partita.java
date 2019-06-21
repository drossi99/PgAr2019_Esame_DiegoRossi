package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import it.unibs.fp.mylib.InputDati;

public class Partita {
	private static final int SALDO_FACILE = 700000;
	private static final int SALDO_MEDIO = 500000;
	private static final int SALDO_DIFFICILE = 200000;
	private static final String SALDO_NON_ACCETTATO = "Il valore inserito non è accettato! RIPROVA";
	private static final String RICHIESTA_SALDO = "Ottimo! Manca poco per iniziare a giocare\nSeleziona quanto avvincente vorrai la tua partita\n\t[1] FACILE:\tSaldo iniziale = 700 000 I€€€\n\t[2] MEDIO:\tSaldo iniziale = 500 000 I€€€\n\t[3] DIFFICILE:\tSaldo iniziale = 200 000 I€€€\n\n\t";

	public static int newPartita() {
		int posizione = 0;
		Giocatore giocatore = Giocatore.creaGiocatore();
		int saldoIniziale;
		
		do {
			saldoIniziale = InputDati.leggiIntero(RICHIESTA_SALDO);
			if  (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3)
				System.out.println(SALDO_NON_ACCETTATO);
		} while (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3);
		
		switch (saldoIniziale) {
		case 1:
			giocatore.setSaldo(SALDO_FACILE);
			break;
		case 2:
			giocatore.setSaldo(SALDO_MEDIO);
			break;
		case 3:
			giocatore.setSaldo(SALDO_DIFFICILE);
			break;
		default:
			break;
		}
		
		ArrayList<Casella> listaCaselle = Gestione.leggiXML();
		int numStazioni = 0;
		ArrayList<Integer> listaPosStazioni = new ArrayList<Integer>();
		
		for (int i = 0; i < listaCaselle.size(); i++) {
			if (listaCaselle.get(i).getType().equals("stazione")) {
				numStazioni++;
				listaPosStazioni.add(i);
			}
		}
		do {
			System.out.println("\n\n*************************************\nSITUAZIONE DI GIOCO\nTi trovi sulla casella");
			stampaCasella(listaCaselle, posizione);
			System.out.println("Hai un saldo di I€€€ "+giocatore.getSaldo());
			System.out.println("*************************************\n\n");
			int lancio = lancioDado(giocatore);
			posizione = posizione + lancio;
			
			if ((posizione) >= listaCaselle.size()){
				posizione = posizione - listaCaselle.size();
			}
			
			System.out.println("Sei finito sulla casella");
			stampaCasella(listaCaselle, posizione);
			
			switch (listaCaselle.get(posizione).getType()) {
			case "iniziale":
				System.out.println("La casella ti partenza ti dà un attimo di tregua. Per questo turno non succederà nulla");
				break;
			case "stazione":
				int k = 1;
				int nuovaStazione;
				
				System.out.println("Una casella di tipo stazione ti permette di raggiungere una delle altre stazioni. In questo tabellone sono presenti " + numStazioni + " stazioni.");
				System.out.println("Seleziona quale stazione vuoi raggiungere: \n\t");
				
				for (int i = 0; i < listaCaselle.size(); i++) {
					if (i != posizione && listaCaselle.get(i).getType().equals("stazione")) {
						System.out.println("\t[" + i + "] " + listaCaselle.get(i).getName());
					}
				}
				
				do {
					nuovaStazione = InputDati.leggiIntero("Se non vuoi trasferirti, digita 0\n\t");
					if (!listaPosStazioni.contains(nuovaStazione) && nuovaStazione != 0)
						System.out.println("Sarebbe bello potersi spostare in qualunque casella, ma non è permesso. RIPROVA");
				} while (!listaPosStazioni.contains(nuovaStazione) && nuovaStazione != 0);
			
				if (nuovaStazione == 0) {
					System.out.println("D'accordo, allora resterai qua");
				} else {
					posizione = nuovaStazione;
					System.out.println("Hai raggiunto con successo la casella stazione ");
					stampaCasella(listaCaselle, posizione);
				}
	
				break;
			case "probabilita":
				giocatore.setSaldo(giocatore.getSaldo() + listaCaselle.get(posizione).getAmount());
				System.out.println("Sei finito su una casella probabilità");
				System.out.println("\tRicevi un premio di " + listaCaselle.get(posizione).getAmount() + " I€€€");
				System.out.println("\tIl tuo nuovo saldo è " + giocatore.getSaldo());
				break;
			case "imprevisto":
				giocatore.setSaldo(giocatore.getSaldo() - listaCaselle.get(posizione).getAmount());
				System.out.println("Sei finito su una casella imprevisti");
				System.out.println("\tTi vengono prelevati " + listaCaselle.get(posizione).getAmount() + " I€€€");
				System.out.println("\tIl tuo nuovo saldo è " + giocatore.getSaldo());
				
				break;
			default:
				break;
			}
			} while (giocatore.getSaldo() > 0 && giocatore.getSaldo() <= 1000000);
		
		if (giocatore.getSaldo() <= 0) return 0;
		
		return 1;
		
		
	}

	public static int lancioDado(Giocatore g){
		int scelta;
		do {
			scelta = InputDati.leggiIntero("È il turno di " + g.getNome() + ". Tocca a te a lanciare il dado\n\t(Premi 1 per lanciare il dado)\n\t");
			if (scelta != 1)
				System.out.println("Tocca a te! Premi 1 per lanciare il dado\n\t");
		} while (scelta != 1);
		
		Random rand = new Random();
		int dado = rand.nextInt(6)+1;
		System.out.println("\tÈ uscito il numero " + dado);
		return dado;
	}
	
	public static void stampaCasella(ArrayList<Casella> lista, int pos) {
		System.out.println("\t" + lista.get(pos).getId());
		System.out.println("\t" + lista.get(pos).getName());
		System.out.println("\t" + lista.get(pos).getType());
	}
}
