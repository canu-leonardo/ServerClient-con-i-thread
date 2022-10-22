package it.fi.meucci;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServer{
    private static ArrayList<Socket> listaSocket = new ArrayList<>();
    private static ArrayList<ServerThread> listaThread = new ArrayList<>();
    private static ServerSocket serverSocket;

    public void avvia(){
        try {
            serverSocket = new ServerSocket(6789);
            do{
                System.out.println("Server in attesa...");
                Socket socket = serverSocket.accept();
                listaSocket.add(socket);
                System.out.println("Socket: " + socket);
                ServerThread serverThread = new ServerThread(socket);
                listaThread.add(serverThread);
                serverThread.start();
                System.out.println();
            }while(/*ServerThread.attivo*/ true);
        } catch (Exception e) {
            System.out.println("SERVER SPENTO");
        }
    }

    static void SpegniServer(){
        for (int i = 0; i < listaSocket.size(); i++){
            try {
                listaSocket.get(i).close();
            } catch (IOException e) {
                System.out.println("errore chiusura socket");
            }
        }
        for (int i = 0; i < listaThread.size(); i++){
            listaThread.get(i).messagioVeloce();
            listaThread.get(i).interrupt();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("errore chiusura server");
        }
    }

    public static Thread getThread(){
        return Thread.currentThread() ;        
    }
}
