package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class Gestione {

	XMLInputFactory xmlif = null;
	XMLStreamReader xmlr = null;
	
	//private String nomeFile;
	
	Casella c = new Casella();
	
	
  
	public ArrayList<Casella> leggiXML(String nomeFile) {
		ArrayList<Casella> listaCaselle = new ArrayList<Casella>();
		
		 //contatore per contare nella listaCitta
		 int j = 0;
		 
		 try {
			 xmlif = XMLInputFactory.newInstance();
			 xmlr = xmlif.createXMLStreamReader(nomeFile, new FileInputStream(nomeFile)); 
			 while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione 
				 switch (xmlr.getEventType()) { // switch sul tipo di evento
				 
				 case XMLStreamConstants.START_DOCUMENT: // inizio del documento: stampa che inizia il documento 
					 //System.out.println("Start Read Doc " + mappa); 
					 break;
			     
				 case XMLStreamConstants.START_ELEMENT: // inizio di un elemento: stampa il nome del tag e i suoi attributi 
			    	 //System.out.println("Tag " + xmlr.getLocalName());
			    	 if (xmlr.getLocalName().equals("cell")) {
			    		 Casella nuovaCitta = new Casella();
			    		 listaCaselle.add(nuovaCitta);
			    		 //System.out.println("nuova citta creata");
			    		 j++;
			    	 }
			         for (int i = 0; i < xmlr.getAttributeCount(); i++) {
			        	 //System.out.printf(" => attributo %s->%s%n", xmlr.getAttributeLocalName(i), xmlr.getAttributeValue(i));
			        	 switch (xmlr.getAttributeLocalName(i)) {
							case "id":
								int idInt = Integer.parseInt(xmlr.getAttributeValue(i));
								listaCaselle.get(j-1).setId(idInt);
								//System.out.println("id aggiunto");
								break;
	
							case "name":
								listaCaselle.get(j-1).setName(xmlr.getAttributeValue(i));
								break;
							case "type":
								listaCaselle.get(j-1).setType(xmlr.getAttributeValue(i));
								break;
		
							default:
								break;
						}
			        	 if (xmlr.getLocalName().equals("amount")) {
				    		 listaCaselle.get(j-1).setAmount(Integer.parseInt(xmlr.getAttributeValue(i)));
				    	 }
			        	 
			        	 if (xmlr.getLocalName().equals("message")) {
			        		 listaCaselle.get(j-1).setListaMessaggi(xmlr.getAttributeValue(i) + "\n");
			        	 }
			         }
			         break;
			         
			     
				 case XMLStreamConstants.END_ELEMENT: // fine di un elemento: stampa il nome del tag chiuso 
			    	 //System.out.println("END-Tag " + xmlr.getLocalName()); 
			    	 break;
			     
				 case XMLStreamConstants.COMMENT:
			         //System.out.println("// commento " + xmlr.getText()); 
			         break; // commento: ne stampa il contenuto
			     
				 case XMLStreamConstants.CHARACTERS: // content all’interno di un elemento: stampa il testo 
			    	 if (xmlr.getText().trim().length() > 0) // controlla se il testo non contiene solo spazi
			         //System.out.println("-> " + xmlr.getText()); 
			    	 break;
			    	 }
			    
				 xmlr.next();
			    
			     }
			 
			 } 
		 catch (Exception e) {
			 //System.out.println("Errore nell'inizializzazione del reader:");
			 //System.out.println(e.getMessage()); 
			 }
		 
		 //System.out.println(listaCitta.size());
		 //System.out.println("\n\n\n");		 
		 
		 
		 
		 return listaCaselle;
	 }
	 
	 
	}

