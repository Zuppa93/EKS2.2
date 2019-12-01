package main.java.client;



import main.java.server.entities.Konto;
import main.java.server.entities.Kunde;
import main.java.server.entities.Transaktion;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


public class startClient {
    public static void main(String [] args){

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:55554/bankservices");
        // 1
        System.out.println("#####################################################################################");
        System.out.println("Teil 1 ");
        System.out.println("#####################################################################################");

        Kunde kundePeter = new Kunde("Peter","Bonn");
        Kunde kundeMichael = new Kunde("Michael","Aachen");
        Kunde kundeKlaus = new Kunde("Klaus","Paderborn");
        // Keine Angaben in .request() weil keine Antwort erwartet wird
        target.path("kunden").request().post(Entity.entity(kundePeter,MediaType.APPLICATION_XML_TYPE));
        target.path("kunden").request().post(Entity.entity(kundeMichael,MediaType.APPLICATION_XML_TYPE));
        target.path("kunden").request().post(Entity.entity(kundeKlaus,MediaType.APPLICATION_XML_TYPE));

        // 2.
        System.out.println("#####################################################################################");
        System.out.println("Teil 2 ");
        System.out.println("#####################################################################################");

        Kunde peter = target.path("kunden/"+kundePeter.getName()).request().accept(MediaType.APPLICATION_XML).get(Kunde.class);
        System.out.println("        Name : "+peter.getName()+" Adresse: "+peter.getAdresse());



        // 3.
        System.out.println("#####################################################################################");
        System.out.println("Teil 3 ");
        System.out.println("#####################################################################################");

        String kNUmmer = target.path("kunden/"+kundePeter.getName()).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.entity("50",MediaType.APPLICATION_XML_TYPE),String.class);
        System.out.println("Kontonummer: "+kNUmmer);
        String kNUmmerMichael = target.path("kunden/"+kundeMichael.getName()).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.entity("80",MediaType.APPLICATION_XML_TYPE),String.class);
        System.out.println("Kontonummer: "+kNUmmerMichael);
        kNUmmer = target.path("kunden/"+kundeKlaus.getName()).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.entity("120",MediaType.APPLICATION_XML_TYPE),String.class);
        System.out.println("Kontonummer: "+kNUmmer);


        // 4.
        System.out.println("#####################################################################################");
        System.out.println("Teil 4 ");
        System.out.println("#####################################################################################");


        kundeMichael = target.path("kunden/"+kundeMichael.getName()).request().accept(MediaType.APPLICATION_XML).get(Kunde.class);
        Transaktion transaktionMichael = new Transaktion(Transaktion.Transaktionstyp.EINZAHLUNG,30,kundeMichael.getKundenKontoByNummer(Integer.parseInt(kNUmmerMichael)));
        Konto kontoMichael = kundeMichael.getKundenKontoByNummer(Integer.parseInt(kNUmmerMichael));
        System.out.println("Kontonummer: "+kontoMichael.getNummer());
        String neueTransaktion = target.path("konten/"+kontoMichael.getNummer()).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.entity(transaktionMichael,MediaType.APPLICATION_XML_TYPE),String.class);
        System.out.println(neueTransaktion);

        // 5.
        System.out.println("#####################################################################################");
        System.out.println("Teil 5 ");
        System.out.println("#####################################################################################");

        Konto[] konten = target.path("kunden/"+kundeMichael.getName()+"/konten").request().accept(MediaType.APPLICATION_XML).get(Konto[].class);
        for(int i = 0; i < konten.length;i++){
            System.out.println("Konto: "+(i+1)+" Nummer: "+konten[i].getNummer()+" Kontostand: "+konten[i].getStand());
        }

        // 6.
        System.out.println("#####################################################################################");
        System.out.println("Teil 6 ");
        System.out.println("#####################################################################################");

        transaktionMichael = new Transaktion(Transaktion.Transaktionstyp.AUSZAHLUNG,20,kundeMichael.getKundenKontoByNummer(Integer.parseInt(kNUmmerMichael)));
        neueTransaktion = target.path("konten/"+kontoMichael.getNummer()).request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).post(Entity.entity(transaktionMichael,MediaType.APPLICATION_XML_TYPE),String.class);
        System.out.println(neueTransaktion);

        // 7.
        System.out.println("#####################################################################################");
        System.out.println("Teil 7 ");
        System.out.println("#####################################################################################");

        String antwort = target.path("konten/"+kNUmmerMichael).request().accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println(antwort);

        // 8.
        System.out.println("#####################################################################################");
        System.out.println("Teil 8 ");
        System.out.println("#####################################################################################");

        /*Professor[] alleProfsAusKoeln =target.path("uniservice").path("profs").queryParam("adresse","Koeln").request().accept(MediaType.APPLICATION_XML).get(Professor[].class);*/
        Transaktion[] transaktionen = target.path("konten/"+kNUmmerMichael+"/transaktionen").queryParam("transaktionstyp","EINZAHLUNG").request().accept(MediaType.APPLICATION_XML).get(Transaktion[].class);
        System.out.println("Transaktionen:");
        for(int i = 0;i < transaktionen.length;i++){
            System.out.println(transaktionen[i].getTransaktionstyp()+" Betrag : "+transaktionen[i].getBetrag());
        }

        // 9.
        System.out.println("#####################################################################################");
        System.out.println("Teil 9 ");
        System.out.println("#####################################################################################");

        /*Professor[] alleProfsAusKoeln =target.path("uniservice").path("profs").queryParam("adresse","Koeln").request().accept(MediaType.APPLICATION_XML).get(Professor[].class);*/
        transaktionen = target.path("konten/"+kNUmmerMichael+"/transaktionen").queryParam("transaktionstyp","AUSZAHLUNG").request().accept(MediaType.APPLICATION_XML).get(Transaktion[].class);
        System.out.println("Transaktionen:");
        for(int i = 0;i < transaktionen.length;i++){
            System.out.println(transaktionen[i].getTransaktionstyp()+" Betrag : "+transaktionen[i].getBetrag());
        }

        // 10.
        System.out.println("#####################################################################################");
        System.out.println("Teil 10 ");
        System.out.println("#####################################################################################");

        Kunde[] response = target.path("kunden").request(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).get(Kunde[].class);
        for(int i = 0; i < response.length;i++){
            System.out.println("Name: "+response[i].getName()+" Adresse: ");
        }
    }
}
