package main.java.server;

import com.sun.net.httpserver.HttpServer;
import main.java.server.resource.ResourceKonto;
import main.java.server.resource.ResourceKunde;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class startServer {
    public static void main(String[]args) throws Exception {
        // Server steht
        URI endpoint = new URI("http://localhost:55554/bankservices");
        ResourceConfig rc = new ResourceConfig(ResourceKunde.class, ResourceKonto.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(endpoint, rc);

    }
}
