package main.java.server.resource;

import main.java.server.entities.Konto;
import main.java.server.entities.Kunde;
import main.java.server.verwaltung.AlleKonten;
import main.java.server.verwaltung.AlleKunden;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("kunden")
public class ResourceKunde {
    private static AlleKunden alleKunden;
    private static AlleKonten alleKonten;

    public ResourceKunde(){
        System.out.println("Hole mir die Liste aller Kunden");
        alleKunden = AlleKunden.getInstance();
        alleKonten = AlleKonten.getInstance();
    }

    // 1.
    // Neuen Kunden erzeugen
    @POST
    //@Path("/")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public void createKunde(Kunde kunde){
        //Kunde kunde = new Kunde(kunde);
        System.out.println("Füge Kunden "+kunde.getName()+" hinzu");
        alleKunden.addKunde(kunde);
        System.out.println("Aktuelle Kunden");
        for(int i = 0 ; i < alleKunden.getAlleKunden().size();i ++){
            System.out.println((i+1)+". "+alleKunden.getAlleKunden().get(i).getName());
        }
    }

    // 2.
    // Kunden anhand seines Namens suchen
    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public Kunde getKundeByName(@PathParam("name") String name){
        return alleKunden.getKundeByName(name);
    }

    // 3.
    /*
     * Kunde mit gegebenem Namen soll ein neues Konto erstellen.
     * Der neue gewünschte Kontostand wird übergeben
     * Der Dienst liefert die neue Kontonummer als String zurück
     * Ein Kunde kann mehrere Konten besitzen
     * Kontonummer muss eindeutig sein
     * */
    @POST
    @Path("{name}")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public String addKonto(@PathParam("name") String name ,String stand){
        int kStand = Integer.parseInt(stand);
        Kunde kunde = alleKunden.getKundeByName(name);

        if(kunde == null ){
            // Angegebener Kunde wurde nicht gefunden
            // TODO geeignete Fehlerausgabe einrichten
            return null;
        }

        Konto konto = new Konto(kStand,kunde);
        System.out.println("Füge nun Konto: "+ konto.getNummer() + " Kunden: "+kunde.getName() +" hinzu");
        alleKonten.addKonto(konto);
        alleKunden.addKontoToKunde(name,konto);
        return Integer.toString(konto.getNummer());
    }

    /*
     * 5.
     * Alle Konten eines Kunden sollen ausgegeben werden
     * */
    @GET
    @Path("{name}/konten")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public ArrayList<Konto> getKontenFromKunde(@PathParam("name") String name){
        Kunde kunde = alleKunden.getKundeByName(name);
        if(kunde == null){
            return null;
        }
        ArrayList<Konto> konten = kunde.getKonten();
        return konten;
    }

    /*
     * 8.
     * Alle Kunden ausgeben
     * */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public ArrayList<Kunde> getAlleKunden(){
        return alleKunden.getAlleKunden();
    }
}
