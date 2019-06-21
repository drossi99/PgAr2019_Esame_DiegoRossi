package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class Gestione {
static boolean booleanMessage;
	static XMLInputFactory xmlif = null;
	static XMLStreamReader xmlr = null;
	
	private static String mappa = "nucleo2_unipoly/1) base (10).xml";
		
	Casella c = new Casella();
		
	public static ArrayList<Casella> leggiXML() {
			 
			 
	ArrayList<Casella> listaCaselle = new ArrayList<Casella>();
			 
			 //contatore per contare nella listaCaselle
			 int j = 0;
			 
			 try {
				 xmlif = XMLInputFactory.newInstance();
				 xmlr = xmlif.createXMLStreamReader(mappa, new FileInputStream(mappa)); 
				 while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione 
					 switch (xmlr.getEventType()) { // switch sul tipo di evento
					 
					 case XMLStreamConstants.START_DOCUMENT: // inizio del documento
						 break;
				     
					 case XMLStreamConstants.START_ELEMENT:
				    	 if (xmlr.getLocalName().equals("cell")) {
				    		 Casella nuovaCasella = new Casella();
				    		 listaCaselle.add(nuovaCasella);
				    		 j++;
				    	 }
				         for (int i = 0; i < xmlr.getAttributeCount(); i++) {
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
				        		
				        		 if (xmlr.getLocalName().equals("line")) {
					        		 listaCaselle.get(j-1).setListaMessaggi(xmlr.getAttributeLocalName(i));
					        	 }
				        	 }
				         }
				         
				         break;
				     
					 case XMLStreamConstants.END_ELEMENT:
				    	 break;
				     
					 case XMLStreamConstants.COMMENT:
				        
				         break;
				     
					 case XMLStreamConstants.CHARACTERS:
				    	 if (xmlr.getText().trim().length() > 0) 
				    	 break;
				    	 }
				    
					 xmlr.next();
				    
				     }
				 } 
			 catch (Exception e) {
				 System.out.println("Errore nell'inizializzazione del reader:");
				 System.out.println(e.getMessage()); 
				 } 
			 
			 return listaCaselle;
		 }
	}