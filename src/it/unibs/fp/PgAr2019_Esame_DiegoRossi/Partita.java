package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import it.unibs.fp.mylib.InputDati;

public class Partita {
	private static final String INIZ = "iniziale";
	private static final String STAZ = "stazione";
	private static final String IMPR = "imprevisto";
	private static final String PROB = "probabilita";
	private static final String PAGA = "\tTi vengono prelevati ";
	private static final String VALUTA = " I€€€";
	private static final String NUOVO_SALDO = "\tIl tuo nuovo saldo è ";
	private static final String PREMIO = "\tRicevi un premio di ";
	private static final String CAS_RAGGIUNTA = "Sei finito su una casella ";
	private static final String SUCCESSO_STAZIONE_RAGGIUNTA = "Hai raggiunto con successo la casella stazione ";
	private static final String RISPOSTA_NULLO_STAZIONE = "Tanto valeva che scegliessi 0";
	private static final String RISPOSTA_NON_STAZIONE = "D'accordo, allora resterai qua";
	private static final String RISPOSTA_STAZIONE_NON_ACCETTATA = "Sarebbe bello potersi spostare in qualunque casella, ma non è permesso. RIPROVA";
	private static final String ZERO_RESTA = "Se non vuoi trasferirti, digita 0\n\t";
	private static final String RICHIESTA_NUOVA_STAZIONE = "Seleziona quale stazione vuoi raggiungere: \n\t";
	private static final String STAZIONI = " stazioni.";
	private static final String STATO_CAS_STAZIONE = "Una casella di tipo stazione ti permette di raggiungere una delle altre stazioni. In questo tabellone sono presenti ";
	private static final String STATO_CAS_INIZIALE = "La casella ti partenza ti dà un attimo di tregua. Per questo turno non succederà nulla";
	private static final String DICHIARAZIONE_LANCIO = "\tÈ uscito il numero ";
	private static final String RICHIESTA_LANCIO = "Tocca a te! Premi 1 per lanciare il dado\n\t";
	private static final String TURNO_DADO = ". Tocca a te a lanciare il dado\n\t(Premi 1 per lanciare il dado)\n\t";
	private static final String TURNO_DI = "È il turno di ";
	private static final String DICHIARAZIONE_SALDO = "Hai un saldo di I€€€ ";
	private static final String BENV_NOME = " Dicci come ti chiami!\n";
	private static final String BENV_GIOC = "\n\n\nBenvenuto giocatore ";
	private static final String FINE_IMMISSIONE_GIOCATORI = "Benissimo, ora siamo al completo! Via alla partita!";
	private static final String STR_NOMI = "Per iniziare, inserite i vostri nomi";
	private static final String BENVENUTI_MULTIPLAYER = "Benvenuti nella modalità multiplayer di Unipoly";
	private static final String DICHIARAZIONE_CASELLA = "Sei finito sulla casella";
	private static final String SEPARATORE = "*************************************\n\n";
	private static final String SITUAZIONE_GIOCO = "\n\n*************************************\nSITUAZIONE DI GIOCO\nTi trovi sulla casella";
	private static final int SALDO_FACILE = 700000;
	private static final int SALDO_MEDIO = 500000;
	private static final int SALDO_DIFFICILE = 200000;
	private static final String SALDO_NON_ACCETTATO = "Il valore inserito non è accettato! RIPROVA";
	private static final String RICHIESTA_SALDO = "Ottimo! Manca poco per iniziare a giocare\nSeleziona quanto avvincente vorrai la tua partita\n\t[1] FACILE:\tSaldo iniziale = 700 000 I€€€\n\t[2] MEDIO:\tSaldo iniziale = 500 000 I€€€\n\t[3] DIFFICILE:\tSaldo iniziale = 200 000 I€€€\n\n\t";

	public static int newPartita() {
		Giocatore giocatore = Giocatore.creaGiocatore();
		int saldoIniziale;
		ArrayList<Integer> listaPosStazioni = new ArrayList<Integer>();
		
		
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
		
		listaPosStazioni = contaStazioni(listaCaselle);
		
		do {
			System.out.println(SITUAZIONE_GIOCO);
			stampaCasella(listaCaselle, giocatore.getPosizione());
			System.out.println(DICHIARAZIONE_SALDO+giocatore.getSaldo());
			System.out.println(SEPARATORE);
			int lancio = lancioDado(giocatore);
			giocatore.setPosizione(giocatore.getPosizione() + lancio);
			
			if ((giocatore.getPosizione()) >= listaCaselle.size()){
				giocatore.setPosizione(giocatore.getPosizione()-listaCaselle.size());
			}
			
			System.out.println(DICHIARAZIONE_CASELLA);
			stampaCasella(listaCaselle, giocatore.getPosizione());
			
			eventoNuovaCasella(listaCaselle, giocatore.getPosizione(), giocatore, listaPosStazioni);
			
			} while (giocatore.getSaldo() > 0 && giocatore.getSaldo() <= 1000000);
		
		if (giocatore.getSaldo() <= 0) return 0;
		
		return 1;
		
		
	}
	
	public static ArrayList<Giocatore> newPartitaMultiplayer(int numGiocatori) {
		int saldoIniziale;
		ArrayList<Integer> listaPosStazioni = new ArrayList<Integer>();
		ArrayList<Integer> classifica = new ArrayList<Integer>();
		ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();
		
		System.out.println(BENVENUTI_MULTIPLAYER);
		
		
		
		System.out.println(STR_NOMI);
		for (int i = 0; i < numGiocatori; i++) {
			System.out.println(BENV_GIOC + (i+1) + BENV_NOME);
			giocatori.add(Giocatore.creaGiocatore());
		}
		System.out.println(FINE_IMMISSIONE_GIOCATORI);
		
		
		
		do {
			saldoIniziale = InputDati.leggiIntero(RICHIESTA_SALDO);
			if  (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3)
				System.out.println(SALDO_NON_ACCETTATO);
		} while (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3);
		
		switch (saldoIniziale) {
		case 1:
			for (int i = 0; i < numGiocatori; i++) {
				giocatori.get(i).setSaldo(SALDO_FACILE);
			}
			
			break;
		case 2:
			for (int i = 0; i < numGiocatori; i++) {
				giocatori.get(i).setSaldo(SALDO_MEDIO);
			}
			break;
		case 3:
			for (int i = 0; i < numGiocatori; i++) {
				giocatori.get(i).setSaldo(SALDO_DIFFICILE);
			}
			break;
		default:
			break;
		}
		
		ArrayList<Casella> listaCaselle = Gestione.leggiXML();
		listaPosStazioni = contaStazioni(listaCaselle);
		
		
		
		do {
			for (int i = 0; i < numGiocatori; i++) {
				System.out.println(SITUAZIONE_GIOCO);
				stampaCasella(listaCaselle, giocatori.get(i).getPosizione());
				System.out.println(DICHIARAZIONE_SALDO+giocatori.get(i).getSaldo());
				System.out.println(SEPARATORE);
				int lancio = lancioDado(giocatori.get(i));
				giocatori.get(i).setPosizione(giocatori.get(i).getPosizione() + lancio);
				
				if ((giocatori.get(i).getPosizione()) >= listaCaselle.size()){
					giocatori.get(i).setPosizione(giocatori.get(i).getPosizione() - listaCaselle.size());
				}
				
				System.out.println(DICHIARAZIONE_CASELLA);
				stampaCasella(listaCaselle, giocatori.get(i).getPosizione());
				
				eventoNuovaCasella(listaCaselle, giocatori.get(i).getPosizione(), giocatori.get(i), listaPosStazioni);
				
				if (giocatori.get(i).getSaldo()  < 0 && giocatori.get(i).getSaldo() >= 1000000)
					return ordinaGiocatori(giocatori);
				
			}
		} while (areSaldiPositivi(giocatori));
			
		return giocatori;
	}

	
	
	public static boolean areSaldiPositivi(ArrayList<Giocatore> listaGiocatori) {
		boolean saldiPositivi = true;
		for (int i = 0; i < listaGiocatori.size(); i++) {
			if (listaGiocatori.get(i).getSaldo() <= 0 || listaGiocatori.get(i).getSaldo() >= 1000000) {
				saldiPositivi = false;
				return saldiPositivi;
			}
		}
		return saldiPositivi;
	}
	public static ArrayList<Giocatore> ordinaGiocatori(ArrayList<Giocatore> listaGiocatori) {
		for(int i = 0; i < listaGiocatori.size(); i++) {
	            boolean flag = false;
	            for(int j = 0; j < listaGiocatori.size()-1; j++) {

	                //Se l' elemento j e maggiore del successivo allora
	                //scambiamo i valori
	                if(listaGiocatori.get(j).getSaldo() > listaGiocatori.get(j+1).getSaldo()) {
	                   Collections.swap(listaGiocatori, i, j);
	                    flag=true; //Lo setto a true per indicare che é avvenuto uno scambio
	                }
	                

	            }

	            if(!flag) break; //Se flag=false allora vuol dire che nell' ultima iterazione
	                             //non ci sono stati scambi, quindi il metodo può terminare
	                             //poiché l' array risulta ordinato
	        }
		return listaGiocatori;
	}
	
	public static int lancioDado(Giocatore g){
		int scelta;
		do {
			scelta = InputDati.leggiIntero(TURNO_DI + g.getNome() + TURNO_DADO);
			if (scelta != 1)
				System.out.println(RICHIESTA_LANCIO);
		} while (scelta != 1);
		
		Random rand = new Random();
		int dado = rand.nextInt(6)+1;
		System.out.println(DICHIARAZIONE_LANCIO + dado);
		return dado;
	}
	
	public static void stampaCasella(ArrayList<Casella> lista, int pos) {
		System.out.println("\t" + lista.get(pos).getId());
		System.out.println("\t" + lista.get(pos).getName());
		System.out.println("\t" + lista.get(pos).getType());
	}
	
	public static void eventoNuovaCasella(ArrayList<Casella> listaCaselle, int posizione, Giocatore giocatore, ArrayList<Integer> listaPosStazioni) {
		switch (listaCaselle.get(posizione).getType()) {
		case INIZ:
			System.out.println(STATO_CAS_INIZIALE);
			break;
		case STAZ:
			int k = 1;
			int nuovaStazione;
			
			System.out.println(STATO_CAS_STAZIONE + listaPosStazioni.size() + STAZIONI);
			System.out.println(RICHIESTA_NUOVA_STAZIONE);
			
			for (int i = 0; i < listaPosStazioni.size(); i++) {
				if (listaPosStazioni.get(i) != posizione) {
					System.out.println("\t[" + listaPosStazioni.get(i) + "] " + listaCaselle.get(listaPosStazioni.get(i)).getName());
				}
			}
			
			do {
				nuovaStazione = InputDati.leggiIntero(ZERO_RESTA);
				if (!listaPosStazioni.contains(nuovaStazione) && nuovaStazione != 0)
					System.out.println(RISPOSTA_STAZIONE_NON_ACCETTATA);
			} while (!listaPosStazioni.contains(nuovaStazione) && nuovaStazione != 0);
		
			if (nuovaStazione == 0) {
				System.out.println(RISPOSTA_NON_STAZIONE);
			} else {
				if (nuovaStazione == posizione)
					System.out.println(RISPOSTA_NULLO_STAZIONE);
				posizione = nuovaStazione;
				System.out.println(SUCCESSO_STAZIONE_RAGGIUNTA);
				stampaCasella(listaCaselle, posizione);
			}

			break;
		case PROB:
			giocatore.setSaldo(giocatore.getSaldo() + listaCaselle.get(posizione).getAmount());
			System.out.println(CAS_RAGGIUNTA + PROB);
			System.out.println(PREMIO + listaCaselle.get(posizione).getAmount() + VALUTA);
			System.out.println(NUOVO_SALDO + giocatore.getSaldo());
			break;
		case IMPR:
			giocatore.setSaldo(giocatore.getSaldo() - listaCaselle.get(posizione).getAmount());
			System.out.println(CAS_RAGGIUNTA + IMPR);
			System.out.println(PAGA + listaCaselle.get(posizione).getAmount() + VALUTA);
			System.out.println(NUOVO_SALDO + giocatore.getSaldo());
			
			break;
		default:
			break;
			}
	}
	
	public static ArrayList<Integer> contaStazioni (ArrayList<Casella> listaCaselle) {
		ArrayList<Integer> listaPosStazioni = new ArrayList<Integer>();
		
		for (int i = 0; i < listaCaselle.size(); i++) {
			if (listaCaselle.get(i).getType().equals(STAZ)) {
				listaPosStazioni.add(i);
			}
		}
		return listaPosStazioni;
	}
}
