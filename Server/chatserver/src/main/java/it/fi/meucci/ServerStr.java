package it.fi.meucci;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStr extends Thread {

    ServerSocket server      = null;
    Socket client            = null;
    String stringaRicevuta   = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoIlClient;

    static boolean aperto = true;


    public ServerStr(){
        try {
            server = new ServerSocket(6789);
            System.out.println("--SERVER APERTO--");
        } catch (Exception e) {
            System.out.println("porta non disponibile");
        }
        
    }

    public void esegui(){
        while(aperto){
            this.attendi();
            this.comunica();
        }
    }

    public void chiudi(){
        this.aperto = false;
    }

    public Socket attendi(){
        try {                       
            client = server.accept();
            // server.close();
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoIlClient = new DataOutputStream(client.getOutputStream());

        } catch (Exception e) {
           
            System.out.println("errore durante la comunicazione");
        
        }

        return client;
    }


    public void comunica(){
        try {
            System.out.println("=================");
            stringaRicevuta = inDalClient.readLine();
            System.out.println("stringa arrivata");
            stringaModificata = stringaRicevuta.toUpperCase();
            System.out.println("elaboro...");
            outVersoIlClient.writeBytes(stringaModificata + "\n");
            System.out.println("stringa modificata e inviata");
    
            client.close();
        } catch (Exception e) {
           
            System.out.println("errore durante la comunicazione");
        
        }

    }
}
