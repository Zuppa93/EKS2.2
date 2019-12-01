package main.java.server.verwaltung;

import main.java.server.entities.Transaktion;

import java.util.ArrayList;

public class AlleTransaktionen {
    private static AlleTransaktionen instance;
    private ArrayList<Transaktion> alleTransaktionen;

    private AlleTransaktionen(){}
    public static AlleTransaktionen getInstance(){
        if(instance == null){
            instance = new AlleTransaktionen();
            return instance;
        }else{
            return instance;
        }
    }
}
