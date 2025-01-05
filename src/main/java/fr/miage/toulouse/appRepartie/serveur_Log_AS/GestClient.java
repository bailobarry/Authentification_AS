package fr.miage.toulouse.appRepartie.serveur_Log_AS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GestClient implements Runnable {
    private final Socket clientSocket;

    public GestClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try{
            BufferedReader entree = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message;
            while ((message = entree.readLine()) != null) {
                // Traitement du message JSON reçu
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
