package fr.miage.toulouse.appRepartie.serveur_AS;

import fr.miage.toulouse.appRepartie.log.JsonLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationTCP {
    private TraiterMessage traiterMessage;
    int port;

    public CommunicationTCP(TraiterMessage traiterMessage, int port) {
        this.traiterMessage = traiterMessage;
        this.port = port;
    }

    public void gererCommunication() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket sockService = serverSocket.accept();
                // Vérifier si le port utilisé est égal au port réservé pour les Managers (28414)
                boolean isManager;
                if(port ==28415){
                    isManager = true;
                }else {
                    isManager = false;
                }

                String typeClient = "";
                if(isManager){
                    typeClient = "Manager";
                }else{
                    typeClient = "Checker";
                }

                System.out.println("Client " + typeClient + " connecté");
                new Thread(new TraiterClient(sockService, traiterMessage, isManager, port)).start();
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
