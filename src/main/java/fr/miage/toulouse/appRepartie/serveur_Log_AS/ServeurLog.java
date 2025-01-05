package fr.miage.toulouse.appRepartie.serveur_Log_AS;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurLog {
    private static final int PORT = 3244;

    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Serveur de journalisation en écoute sur le port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new GestClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
