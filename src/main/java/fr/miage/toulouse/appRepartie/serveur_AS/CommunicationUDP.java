package fr.miage.toulouse.appRepartie.serveur_AS;

import fr.miage.toulouse.appRepartie.log.JsonLogger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class CommunicationUDP {
    private final TraiterMessage traiterMessage;
    int port;

    public CommunicationUDP(TraiterMessage traiterMessage, int port) {
        this.traiterMessage = traiterMessage;
        this.port = port;
    }

    public void gererCommunication() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port);
        } catch(SocketException se) {
            throw new RuntimeException(se);
        }
        while(true) {
            DatagramPacket entree = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(entree);

                // Vérifier si le port utilisé est égal au port réservé pour les Managers (28415)
                boolean isManager;
                if (port == 28415) {
                    isManager = true; // Le client est un Manager
                } else {
                    isManager = false; // Le client est un Checker
                }

                String typeClient = "";
                if(isManager) {
                    typeClient = "Manager";
                }else{
                    typeClient = "Checker";
                }

                String requete = new String(entree.getData(), 0, entree.getLength());
                System.out.println("Requete UDP reçu du " + typeClient + " : " + requete);

                String reponse = "";
                String login = requete.split(" ")[1];
                String type = requete.split(" ")[0];

                if (!isManager && !requete.startsWith("CHK")) {
                    reponse = "ERROR: Access Denied";
                } else {
                    reponse = traiterMessage.traitement(requete);
                }

                // Journalisation de la requête UDP
                JsonLogger.log(
                    entree.getAddress().getHostAddress(),
                    port,
                    "UDP",
                    type,
                    login,
                    reponse
                );

                DatagramPacket envoi = new DatagramPacket(reponse.getBytes(), reponse.length(), entree.getAddress(), entree.getPort());
                socket.send(envoi);
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
