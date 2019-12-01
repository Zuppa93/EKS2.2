package main.java.server.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Kunde {
    @XmlElement
    private String name;

    @XmlElement
    private String adresse;

    @XmlElement
    private ArrayList<Konto> konten;

    public Kunde(){}

    public Kunde(String name,String adresse){
        this.name = name;
        this.adresse = adresse;
        konten = new ArrayList<Konto>();
    }

    public String getName(){
        return name;
    }

    public void addKonto(Konto konto){
        if(konten == null){
            konten = new ArrayList<Konto>();
        }

        try {
            konten.add(konto);
        }catch(Exception e){
            System.out.println("Fehler beim hinzufügen des Kontos");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Konto> getKonten(){
        return konten;
    }

    public Konto getKundenKontoByNummer(int nummer){
        Konto konto=null;
        for(int i = 0; i < konten.size();i++){
            if(konten.get(i).getNummer() == nummer){
                konto = konten.get(i);
            }
        }
        return konto;
    }
}
