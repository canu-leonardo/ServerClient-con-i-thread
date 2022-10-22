package it.fi.itismeucci;

import java.io.*;

public class AltroAscoltatore extends Thread{
    
    BufferedReader inDalServer;
    Thread client;
    Client capo;

    public AltroAscoltatore (BufferedReader b, Thread ThreadClient, Client c){
        inDalServer = b;
        client = ThreadClient;
        this.capo = c;
    }

    public void run(){
        String stringaRicevuta = null;

        while (true){
            try {
                stringaRicevuta = inDalServer.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
    
    
            if (stringaRicevuta.equals("spegniti")){
                client.interrupt();
            }else{
                //System.out.println("il thread ha ricevuto la stringa");
                System.out.println("Ricevi <  " + stringaRicevuta);
                System.out.println("=====");
                System.out.print("Scrivi >  ");
            }
        }

        /* System.out.println("Ricevi <  " + stringaRicevuta);
        System.out.println("====="); */
    }
}
