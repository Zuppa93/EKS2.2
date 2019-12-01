package main.java.server.verwaltung;


import main.java.server.entities.Konto;
import main.java.server.entities.Kunde;

import java.util.ArrayList;

public class AlleKunden {
    private static AlleKunden instance;
    private ArrayList<Kunde> alleKunden;

    private AlleKunden(){
        alleKunden = new ArrayList<Kunde>();
    }

    public static AlleKunden getInstance(){
        if(instance == null)
            return instance = new AlleKunden();
        return instance;
    }

    public void addKunde(Kunde kunde){
        alleKunden.add(kunde);
    }

    public Kunde getKundeByName(String name){
        Kunde kunde = null;
        System.out.println("Starte die Suche nach dem Kunden");
        for(int i = 0;i < alleKunden.size();i++){
            if(alleKunden.get(i).getName().equals(name)){
                kunde = alleKunden.get(i);
            }
        }
        System.out.println("Gefundener Kunde:"+kunde.getName());
        return kunde;
    }

    public int getKundenIndex(String name){
        for(int i = 0; i < alleKunden.size();i++){
            if(alleKunden.get(i).getName().equals(name)){
                System.out.println("Gesuchter Kunde ist an Stelle "+i);
                return i;
            }

        }
        return -1;
    }

    public void addKontoToKunde(String name, Konto konto){
        Kunde kunde = getKundeByName(name);
        kunde.addKonto(konto);
        alleKunden.set(getKundenIndex(name),kunde);
    }

    public ArrayList<Kunde> getAlleKunden(){
        return alleKunden;
    }
}
