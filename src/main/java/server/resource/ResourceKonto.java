package main.java.server.resource;

import main.java.server.entities.Konto;
import main.java.server.entities.Transaktion;
import main.java.server.verwaltung.AlleKonten;
import main.java.server.verwaltung.AlleKunden;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("konten")
public class ResourceKonto {

    private AlleKonten alleKonten;
    private AlleKunden alleKunden;

    public ResourceKonto(){
        alleKonten = AlleKonten.getInstance();
        alleKunden = AlleKunden.getInstance();
    }


    // 4.
    /* Für ein Konto mit gegebener Nummer
     *  @Path /konten/{nummer}
     * Neue Transaktion anlegen
     * @Post
     *
     * Antwort Name des Kunden, neuer Kontostand
     *
     *
     * */

    @Path("{nummer}")
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public String addTransaktion(@PathParam("nummer") String nummer, Transaktion transaktion){

        Konto konto = alleKonten.getKontoByNummer(Integer.parseInt(nummer));

        if(konto == null){
            return "Konto not found";
        }

        if(transaktion.getTransaktionstyp().equals("EINZAHLUNG")){
            transaktion = new Transaktion(Transaktion.Transaktionstyp.EINZAHLUNG,transaktion.getBetrag(),konto);
        } else if (transaktion.getTransaktionstyp().equals("AUSZAHLUNG")){
            transaktion = new Transaktion(Transaktion.Transaktionstyp.AUSZAHLUNG,transaktion.getBetrag(),konto);
        }else{
            return "Ambivalenter TransaktionsTyp";
        }

        alleKonten.getKontoByNummer(Integer.parseInt(nummer)).addTransaktion(transaktion);

        return konto.getBesitzer().getName() + " " + Integer.toString(konto.getStand());
    }

    /*
     * 6.
     * FÚr ein Konto mit gegebener NUmmer soll der Kontostand erfragt
     * und als String zurückgegeben werden
     * */
    @Path("{nummer}")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public String getStand(@PathParam("nummer") int nummer){
        Konto konto = alleKonten.getKontoByNummer(nummer);
        return Integer.toString(konto.getStand());
    }

    /*
     * 7.
     * Alle Transaktionen eines Kontos mit gegebener Nummer
     * zurückgeben
     */
    @Path("{nummer}/transaktionen")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public ArrayList<Transaktion> getTransaktionenFromKonto(@PathParam("nummer") int nummer){
        Konto konto = alleKonten.getKontoByNummer(nummer);
        return konto.getTransaktionen();
    }
}
