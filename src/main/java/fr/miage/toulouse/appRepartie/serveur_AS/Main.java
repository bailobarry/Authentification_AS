package fr.miage.toulouse.appRepartie.serveur_AS;

import fr.miage.toulouse.appRepartie.metier.ListeAuth;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int portChecker = 28414;
        int portManager = 28415;

        ListeAuth metier = new ListeAuth();
        TraiterMessage traiterMessage = new TraiterMessage(metier);
        Multi_Protocole communiquer = new Multi_Protocole(traiterMessage, portChecker, portManager);

        Thread serveur = new Thread(communiquer);
        serveur.start();
    }
}