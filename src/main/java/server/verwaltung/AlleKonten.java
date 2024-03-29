package main.java.server.verwaltung;

import main.java.server.entities.Konto;
import main.java.server.entities.Transaktion;

import java.util.ArrayList;

public class AlleKonten {
    private static AlleKonten instance;
    private ArrayList<Konto> alleKonten;

    private AlleKonten(){
        alleKonten = new ArrayList<Konto>();
    }

    public static AlleKonten getInstance(){
        if(instance == null)
            return instance = new AlleKonten();
        return instance;
    }

    public Konto getKontoByNummer(int nummer){
        Konto konto = null;
        System.out.println("Suche Konto nach Nummer");
        for(int i = 0 ; i < alleKonten.size();i++){
            if(alleKonten.get(i).getNummer() == nummer){
                konto = alleKonten.get(i);
            }
        }
        return konto;

    }

    public void addTransaktionToKonto(Konto konto, Transaktion transaktion){
        if(alleKonten == null){
            alleKonten = new ArrayList<Konto>();
        }
        getKontoByNummer(konto.getNummer()).addTransaktion(transaktion);
    }


    public void addKonto(Konto konto){
        alleKonten.add(konto);
    }
}
