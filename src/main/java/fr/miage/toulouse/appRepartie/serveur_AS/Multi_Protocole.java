package fr.miage.toulouse.appRepartie.serveur_AS;

public class Multi_Protocole implements Runnable {
    private final TraiterMessage traiterMessage;
    private final int portChecker;
    private final int portManager;

    public Multi_Protocole(TraiterMessage traiterMessage, int portChecker, int portManager) {
        this.traiterMessage = traiterMessage;
        this.portChecker = portChecker;
        this.portManager = portManager;
    }

    @Override
    public void run() {
        // Thread pour gérer les communications UDP pour le Checker
        Thread udpChecker = new Thread(() -> {
            CommunicationUDP communicationUDP = new CommunicationUDP(traiterMessage, portChecker);
            communicationUDP.gererCommunication();
        });

        // Thread pour gérer les communications UDP pour le Manager
        Thread udpManager = new Thread(() -> {
            CommunicationUDP communicationUDP = new CommunicationUDP(traiterMessage, portManager);
            communicationUDP.gererCommunication();
        });

        // Thread pour gérer les communications TCP pour le Checker
        Thread tcpChecker = new Thread(() -> {
            CommunicationTCP communicationTCP = new CommunicationTCP(traiterMessage, portChecker);
            communicationTCP.gererCommunication();
        });

        // Thread pour gérer les communications TCP pour le Manager
        Thread tcpManager = new Thread(() -> {
            CommunicationTCP communicationTCP = new CommunicationTCP(traiterMessage, portManager);
            communicationTCP.gererCommunication();
        });

        // Lancer les threads
        udpChecker.start();
        udpManager.start();
        tcpChecker.start();
        tcpManager.start();
        System.out.println("Serveur demarré sur le port " + portChecker + " et sur le port " + portManager + " ...");
    }
}
