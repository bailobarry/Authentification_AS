package fr.miage.toulouse.appRepartie.serveur_AS;

import fr.miage.toulouse.appRepartie.log.JsonLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class TraiterClient implements Runnable {
    private final Socket sockService;
    private final TraiterMessage traiterMessage;
    private final boolean isManager;
    private final int port;

    public TraiterClient(Socket sockService, TraiterMessage traiterMessage, boolean isManager, int port) {
        this.sockService = sockService;
        this.traiterMessage = traiterMessage;
        this.isManager = isManager;
        this.port = port;
    }

    @Override
    public void run() {
        BufferedReader entree = null;
        PrintStream sortie = null;
        try{
            entree = new BufferedReader(new InputStreamReader(sockService.getInputStream()));
            sortie = new PrintStream(sockService.getOutputStream());
            String requete = "";
            try{
                while((requete = entree.readLine()) != null){
                    System.out.println("Requete TCP reçu : "+requete);
                    String reponse = "";

                    String login = requete.split(" ")[1];
                    String type = requete.split(" ")[0];

                    if (!isManager && !requete.startsWith("CHK")) {
                        reponse = "ERROR: Access Denied";
                    } else {
                        reponse = traiterMessage.traitement(requete);
                    }
                    
                    // Journalisation de la requête
                    JsonLogger.log(
                        sockService.getInetAddress().getHostAddress(),
                            port,
                        "TCP",
                        type,
                        login,
                        reponse
                    );
                    
                    sortie.println(reponse);
                }
            }catch(Exception e){
                throw new RuntimeException(e);
            }
            sockService.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
