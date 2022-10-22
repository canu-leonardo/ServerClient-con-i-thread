package it.fi.meucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    ServerSocket server      = null;
    Socket client            = null;
    String stringaRicevuta   = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoIlClient;
    //static boolean attivo = true;

    public ServerThread (Socket socket){
        this.client = socket;
    }

    public void run(){
        try {
            comunica();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoIlClient = new DataOutputStream(client.getOutputStream());

        for (;;){
            stringaRicevuta = inDalClient.readLine();
            if (stringaRicevuta.equals("SPEGNI")){                
                //ServerThread.attivo = false;
                MultiServer.SpegniServer();
                //MultiServer.getThread().interrupt();
                //outVersoIlClient.writeBytes(stringaRicevuta + "( => il server si sta spegnendo...)" + "\n");
                break;
            }
            if (stringaRicevuta.equals("FINE")){
                outVersoIlClient.writeBytes(stringaRicevuta + " ( => chiusura socket...)" + "\n");
                break;
            }
            else{
                outVersoIlClient.writeBytes(stringaRicevuta.toUpperCase()+ "\n");
                System.out.println("fine elaborazione del socket: " + client);
            }
        }
        outVersoIlClient.close();
        inDalClient.close();
        client.close();
    }

    public Socket getSocket(){
        return this.client;
    }

    public void messagioVeloce(){
        try {
            outVersoIlClient.writeBytes("spegniti");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
