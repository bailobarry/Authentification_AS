package fr.miage.toulouse.appRepartie.clients;

import java.io.*;
import java.net.*;
import javax.json.*;
import fr.miage.toulouse.appRepartie.log.JsonLogger;

public class ClientAS {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 3244;

    public static void main(String[] args) {
        String login = "user"; // Remplacez par le login approprié
        String result = "success"; // Remplacez par le résultat approprié

        // Exemple d'envoi d'un message JSON et journalisation
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true)) {

            // Création d'un message JSON
            JsonObject jsonMessage = Json.createObjectBuilder()
                    .add("type", "log")
                    .add("message", "Ceci est un message de journalisation.")
                    .build();

            // Envoi du message JSON au serveur
            sortie.println(jsonMessage.toString());
            System.out.println("Message envoyé: " + jsonMessage);

            // Journaliser l'opération
            JsonLogger.log(SERVER_ADDRESS, SERVER_PORT, "TCP", "log", login, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
