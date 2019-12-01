package main.java.server.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@XmlRootElement
public class Konto {
    @XmlElement
    private int stand;
    @XmlElement
    private int nummer;
    //@XmlElement
    private Kunde besitzer;
    @XmlElement
    private ArrayList<Transaktion> transaktionen;

    private static int lastNumber;

    public Konto(){}

    public Konto(int stand, Kunde besitzer){
        this.besitzer = besitzer;
        this.stand = stand;
        lastNumber++;
        nummer = lastNumber;
        transaktionen = new ArrayList<Transaktion>();
    }

    public int getNummer(){
        return nummer;
    }

    public ArrayList<Transaktion> getTransaktionen(){
        return transaktionen;
    }

    public ArrayList<Transaktion> getTransaktionen(Transaktion.Transaktionstyp transaktionstyp){
        ArrayList<Transaktion> transaktionenTemp = new ArrayList<Transaktion>();
        for(int i = 0; i < transaktionen.size();i++){
            if(transaktionstyp == transaktionen.get(i).getTransaktionstyp()){
                transaktionenTemp.add(transaktionen.get(i));
            }
        }
        return transaktionenTemp;
    }

    public int getStand(){return stand;}
    public Kunde getBesitzer(){return besitzer;}

    public void addTransaktion(Transaktion transaktion){

        if(transaktion.getTransaktionstyp() == Transaktion.Transaktionstyp.EINZAHLUNG){
            stand += transaktion.getBetrag();
        }
        if ((transaktion.getTransaktionstyp() == Transaktion.Transaktionstyp.AUSZAHLUNG)){
            stand -= transaktion.getBetrag();
        }
        transaktionen.add(transaktion);
    }

}
