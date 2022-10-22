package it.fi.itismeucci;

import java.io.*;
import java.net.*;

public class Client {
    String nomeServer = "localhost";
    int porta = 6789;
    Socket mioSocket;
    BufferedReader tastiera;
    String stringaUtente;
    String stringaRicevuta;
    DataOutputStream outVersoIlServer;
    BufferedReader inDalServer;

    AltroAscoltatore ThreadDalServer;

    public Socket connetti(){
        try {

            tastiera = new BufferedReader(new InputStreamReader(System.in));
            mioSocket = new Socket(nomeServer, porta);

            outVersoIlServer = new DataOutputStream(mioSocket.getOutputStream());

            inDalServer = new BufferedReader(new InputStreamReader( mioSocket.getInputStream()));
            ThreadDalServer = new AltroAscoltatore(inDalServer, Thread.currentThread(), this); //creo il secondo thread
            ThreadDalServer.start();// lo avvio

        } catch (Exception e) {
           
            System.out.println("===DISCONNESSO DAL SERVER===");
        
        }
        return mioSocket;
    } 

    public void Comunica(){
        String stringaUtente = "";
        System.out.println("Scrivi > ");
        try {            
            while ( !stringaUtente.equals("FINE") && !stringaUtente.equals("SPEGNI")){
                //System.out.print("Scrivi >  ");
                stringaUtente = tastiera.readLine();
                outVersoIlServer.writeBytes(stringaUtente + "\n");
                
                /*System.out.println("Ricevi <  " + stringaRicevuta);
                System.out.println("=====");*/
            }
            mioSocket.close();
        } catch (Exception e) {           
            System.out.println("IL SERVER NON é RIUSCITO A RISPONDERE");
        }
    }
}
