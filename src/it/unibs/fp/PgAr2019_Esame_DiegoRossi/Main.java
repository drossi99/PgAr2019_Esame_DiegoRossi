package it.unibs.fp.PgAr2019_Esame_DiegoRossi;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Gestione g = new Gestione();
		ArrayList<Casella> listaCaselle = g.leggiXML("nucleo2_unipoly/1)base(10).xml");
		System.out.println(listaCaselle);
	}

}
