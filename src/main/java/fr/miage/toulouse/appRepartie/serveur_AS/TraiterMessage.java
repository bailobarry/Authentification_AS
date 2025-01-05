package fr.miage.toulouse.appRepartie.serveur_AS;

import fr.miage.toulouse.appRepartie.metier.ListeAuth;

public class TraiterMessage {

    private ListeAuth liste;

    public TraiterMessage(ListeAuth listeAuth) {
        this.liste = listeAuth;
    }

    public String traitement(String requete){
        String[] decoupe = requete.split(" ");
        String reponse = "";
        if(decoupe.length == 3){
            String commande = decoupe[0];
            String login = decoupe[1];
            String password = decoupe[2];

            switch (commande){
                case "CHK":
                    if(liste.tester(login, password)){
                        reponse = "GOOD";
                    }else{
                        reponse = "BAD";
                    }
                    break;
                case "ADD":
                    if(liste.creer(login, password)){
                        reponse = "DONE";
                    }else{
                        reponse = "ERROR EXIST";
                    }
                    break;
                case "DEL":
                    if(liste.supprimer(login, password)){
                        reponse = "DONE";
                    }else{
                        reponse = "ERROR NOT FOUND";
                    }
                    break;
                case "MOD":
                    if(liste.mettreAJour(login, password)){
                        reponse = "DONE";
                    }else {
                        reponse = "ERROR NOT FOUND";
                    }
                    break;
                default:
                    reponse = "COMMAND ERROR";
                    break;
            }
        }
        return reponse;
    }
}
