package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import it.unibs.fp.mylib.InputDati;

/**
 * @author diego
 *
 */
/**
 * @author diego
 *
 */
/**
 * @author diego
 *
 */
public class Partita {
	private static final int LIMITE_AVVISO_POSITIVO = 900000;
	private static final int LIMITE_AVVISO_NEGATIVO = 100000;
	private static final String AVVISO_POSITIVO = "\tManca poco alla vittoria, ora vedi di non spendere troppo!";
	private static final String AVVISO_NEGATIVO = "\tAttenzione alle tue finanze!";
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
	private static final String BENV_NOME = "\n\tDicci come ti chiami!\n";
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

	/**
	 * newPartitaSingleplayer, crea una nuova partita con 1 giocatore
	 * @return int (1) se il giocatore vince, (0) se perde
	 */
	public static int newPartitaSingleplayer() {
		//viene creato un nuovo giocatore
		Giocatore giocatore = Giocatore.creaGiocatore();
		int saldoIniziale;
		//qui verranno salvate le posizioni assolute delle stazioni nella listaCaselle
		ArrayList<Integer> listaPosStazioni = new ArrayList<Integer>();
		
		/*
		 * si chiede il livello di difficoltà (viene cambiato il saldo: facile = saldo alto / difficile = saldo basso)
		 * finché il valore è valido (1, 2, 3)
		 */
		do {
			saldoIniziale = InputDati.leggiIntero(RICHIESTA_SALDO);
			if  (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3)
				System.out.println(SALDO_NON_ACCETTATO);
		} while (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3);
		
		//in base alla scelta viene settato il saldo del giocatore
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
		
		//viene letto il file XML
		ArrayList<Casella> listaCaselle = Gestione.leggiXML();
		
		//vengono contate le stazioni all'interno del tabellone di gioco
		listaPosStazioni = contaStazioni(listaCaselle);
		
		//finché il saldo del giocatore è valido (da 0 a 1 000 000 esclusi) gli viene dato il turno	
		do {
			System.out.println(SITUAZIONE_GIOCO);
			//metodo per la stampa degli attributi utili di una casella
			stampaCasella(listaCaselle, giocatore.getPosizione());
			System.out.println(DICHIARAZIONE_SALDO+giocatore.getSaldo());
			avvisoSaldo(giocatore.getSaldo());
			System.out.println(SEPARATORE);

			//lancio di un dado (da 1 a 6 inclusi);
			int lancio = lancioDado(giocatore);
			//in base all'esito del lancio del dado viene modificata la posizione del giocatore
			giocatore.setPosizione(giocatore.getPosizione() + lancio);
			
			/*
			 * se la posizione è maggiore del numero di caselle del tabellone,
			 * viene tolto il numero di caselle totali, in modo da non finire
			 * in OutOfBounds
			 */
			if ((giocatore.getPosizione()) >= listaCaselle.size()){
				giocatore.setPosizione(giocatore.getPosizione()-listaCaselle.size());
			}
			
			//viene dichiarata la situazione del giocatore dopo lo spostamento
			System.out.println(DICHIARAZIONE_CASELLA);
			stampaCasella(listaCaselle, giocatore.getPosizione());
			
			//in base a dove si finisce viene modificato l'evento
			eventoNuovaCasella(listaCaselle, giocatore.getPosizione(), giocatore, listaPosStazioni);
			
			} while (giocatore.getSaldo() > 0 && giocatore.getSaldo() < 1000000);
		
		//se il saldo finisce negativo, il giocatore perde
		if (giocatore.getSaldo() <= 0) return 0;
		
		//ALTRIMENTI return 1 (vince)
		return 1;
		
		
	}
	
	
	
	/**
	 * metodo per gestione della partita in multiplayer
	 * @param numGiocatori : int
	 * @return listaOrdinata in base al saldo
	 */
	public static ArrayList<Giocatore> newPartitaMultiplayer(int numGiocatori) {
		int saldoIniziale;
		ArrayList<Integer> listaPosStazioni = new ArrayList<Integer>();
		ArrayList<Integer> classifica = new ArrayList<Integer>();
		ArrayList<Giocatore> giocatori = new ArrayList<Giocatore>();
		
		System.out.println(BENVENUTI_MULTIPLAYER);
		
		System.out.println(STR_NOMI);
		
		//creazione di quanti giocatori sono stati richiesti
		for (int i = 0; i < numGiocatori; i++) {
			System.out.println(BENV_GIOC + (i+1) + BENV_NOME);
			giocatori.add(Giocatore.creaGiocatore());
		}
		System.out.println(FINE_IMMISSIONE_GIOCATORI);
		
		/*
		 * si chiede il livello di difficoltà (viene cambiato il saldo: facile = saldo alto / difficile = saldo basso)
		 * finché il valore è valido (1, 2, 3)
		 */
		do {
			saldoIniziale = InputDati.leggiIntero(RICHIESTA_SALDO);
			if  (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3)
				System.out.println(SALDO_NON_ACCETTATO);
		} while (saldoIniziale != 1 && saldoIniziale != 2 && saldoIniziale != 3);
		
		//in base alla scelta viene settato il saldo dei giocatori
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
		
		//viene letto il file XML
		ArrayList<Casella> listaCaselle = Gestione.leggiXML();
		
		//vengono contate le stazioni all'interno del tabellone di gioco
		listaPosStazioni = contaStazioni(listaCaselle);
		
		
		//finché TUTTI i saldi dei giocatori sono positivi viene dato il turno
		do {
			//appena un giocatore ha saldo non accettato, viene terminato il giro di turni
			for (int i = 0; i < numGiocatori; i++) {
				System.out.println(SITUAZIONE_GIOCO);
				stampaCasella(listaCaselle, giocatori.get(i).getPosizione());
				System.out.println(DICHIARAZIONE_SALDO+giocatori.get(i).getSaldo());
				avvisoSaldo(giocatori.get(i).getSaldo());
				System.out.println(SEPARATORE);
				int lancio = lancioDado(giocatori.get(i));
				giocatori.get(i).setPosizione(giocatori.get(i).getPosizione() + lancio);
				
				if ((giocatori.get(i).getPosizione()) >= listaCaselle.size()){
					giocatori.get(i).setPosizione(giocatori.get(i).getPosizione() - listaCaselle.size());
				}
				
				System.out.println(DICHIARAZIONE_CASELLA);
				stampaCasella(listaCaselle, giocatori.get(i).getPosizione());
				
				eventoNuovaCasella(listaCaselle, giocatori.get(i).getPosizione(), giocatori.get(i), listaPosStazioni);
				
				//se il saldo è negativo, viene formata la classifica
				if (giocatori.get(i).getSaldo()  < 0 && giocatori.get(i).getSaldo() >= 1000000)
					return ordinaGiocatori(giocatori);
				
			}
		} while (areSaldiPositivi(giocatori));
			
		//return classifica ordinata
		return giocatori;
	}

	
	public static void avvisoSaldo(int saldo) {
		if (saldo<LIMITE_AVVISO_NEGATIVO)
			System.out.println(AVVISO_NEGATIVO);
		else {
			if (saldo>LIMITE_AVVISO_POSITIVO)
				System.out.println(AVVISO_POSITIVO);
		}
	}
	
	
	/**
	 * @param listaGiocatori
	 * @return boolean, true se TUTTI i saldi sono positivi
	 */
	public static boolean areSaldiPositivi(ArrayList<Giocatore> listaGiocatori) {
		boolean saldiPositivi = true;
		//appena si vede che un giocatore ha saldo negativo, return false
		for (int i = 0; i < listaGiocatori.size(); i++) {
			if (listaGiocatori.get(i).getSaldo() <= 0 || listaGiocatori.get(i).getSaldo() >= 1000000) {
				saldiPositivi = false;
				return saldiPositivi;
			}
		}
		return saldiPositivi;
	}
	
	
	/**
	 * ordina ordina giocatori secondo il saldo
	 * @param listaGiocatori
	 * @return lsitaGiocatori ordinata
	 */
	public static ArrayList<Giocatore> ordinaGiocatori(ArrayList<Giocatore> listaGiocatori) {
		for(int i = 0; i < listaGiocatori.size(); i++) {
	            boolean flag = false;
	            for(int j = 0; j < listaGiocatori.size()-1; j++) {

	                /*
	                 * Se l' elemento j è maggiore del successivo allora
	                 * scambio i valori
	                 */
	               if(listaGiocatori.get(j).getSaldo() < listaGiocatori.get(j+1).getSaldo()) {
	                   Collections.swap(listaGiocatori, i, j);
	                   //setto a true per indicare che é avvenuto uno scambio
	                   flag=true;
	                }
	                

	            }

	            /*
	             * se è false non è stata fatta alcuna modifica perché la lista
	             * risulta già ordinata
	             */
	            if(!flag) break; 
	        }
		return listaGiocatori;
	}
	

	/**
	 * @param giocatore, (per il nome)
	 * @return int da 1 a 6 inclusi che simula un dado
	 */
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
	
	
	/**
	 * stampta gli attributi di una casella utili in un turno (id, nome, tipo)
	 * @param lista delle caselle
	 * @param pos, posizione della casella da stampare
	 */
	public static void stampaCasella(ArrayList<Casella> lista, int pos) {
		System.out.println("\t" + lista.get(pos).getId());
		System.out.println("\t" + lista.get(pos).getName());
		System.out.println("\t" + lista.get(pos).getType());
	}
	
	
	/**
	 * @param listaCaselle, lista delle caselle del tabellone
	 * @param posizione, posizione della casella cui far riferimento
	 * @param giocatore, per modificare il saldo
	 * @param listaPosStazioni, se la casella è una stazione serve far vedere la lista stazioni in cui
	 * ci si potrà muovere
	 */
	public static void eventoNuovaCasella(ArrayList<Casella> listaCaselle, int posizione, Giocatore giocatore, ArrayList<Integer> listaPosStazioni) {
		//viene switchato il tipo della casella
		switch (listaCaselle.get(posizione).getType()) {
		case INIZ:
			//se si tratta della casella iniziale non deve succedere niente
			System.out.println(STATO_CAS_INIZIALE);
			break;
		case STAZ:
			int nuovaStazione;
			
			System.out.println(STATO_CAS_STAZIONE + listaPosStazioni.size() + STAZIONI);
			System.out.println(RICHIESTA_NUOVA_STAZIONE);
			
			//vengono stampate le stazioni in cui è possibile spostarsi
			for (int i = 0; i < listaPosStazioni.size(); i++) {
				if (listaPosStazioni.get(i) != posizione) {
					System.out.println("\t[" + listaPosStazioni.get(i) + "] " + listaCaselle.get(listaPosStazioni.get(i)).getName());
				}
			}
			
			//viene richiesta la posizione della stazione in cui ci si vuole muovere, finché il valore è accettato
			do {
				nuovaStazione = InputDati.leggiIntero(ZERO_RESTA);
				if (!listaPosStazioni.contains(nuovaStazione) && nuovaStazione != 0)
					System.out.println(RISPOSTA_STAZIONE_NON_ACCETTATA);
			} while (!listaPosStazioni.contains(nuovaStazione) && nuovaStazione != 0);
		
			//con 0 non succede niente
			if (nuovaStazione == 0) {
				System.out.println(RISPOSTA_NON_STAZIONE);
			} else {
				/*
				 * se il numero è uguale alla posizione attuale, viene comunque accettato
				 * ma viene stampato un messaggio
				 */
				if (nuovaStazione == posizione)
					System.out.println(RISPOSTA_NULLO_STAZIONE);
				posizione = nuovaStazione;
				
				//stazione raggiunta con successo
				System.out.println(SUCCESSO_STAZIONE_RAGGIUNTA);
				stampaCasella(listaCaselle, posizione);
			}

			break;
		case PROB:
			//su casella probabilità il saldo viene aumentato
			giocatore.setSaldo(giocatore.getSaldo() + listaCaselle.get(posizione).getAmount());
			System.out.println(CAS_RAGGIUNTA + PROB);
			System.out.println(PREMIO + listaCaselle.get(posizione).getAmount() + VALUTA);
			System.out.println(NUOVO_SALDO + giocatore.getSaldo());
			break;
		case IMPR:
			//su casella imprevisti il saldo viene diminuito
			giocatore.setSaldo(giocatore.getSaldo() - listaCaselle.get(posizione).getAmount());
			System.out.println(CAS_RAGGIUNTA + IMPR);
			System.out.println(PAGA + listaCaselle.get(posizione).getAmount() + VALUTA);
			System.out.println(NUOVO_SALDO + giocatore.getSaldo());
			
			break;
		default:
			break;
			}
	}
	
	/**
	 * @param listaCaselle
	 * @return lsita con posizioni delle stazioni in un tabellone (verifica sulla stringa type)
	 */
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
