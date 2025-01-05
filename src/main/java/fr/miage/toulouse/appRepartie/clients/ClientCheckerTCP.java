package fr.miage.toulouse.appRepartie.clients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientCheckerTCP {
    public static void main(String[] args) {
        Socket sockClient = null;
        try{
            sockClient = new Socket("localhost", 28414);
            System.out.println("Connecté au serveur avec port 28414");

            BufferedReader entree = null;
            PrintStream sortie = null;
            try{
                entree = new BufferedReader(new InputStreamReader(sockClient.getInputStream()));
                sortie = new PrintStream(sockClient.getOutputStream());

                String requete = null;

                Scanner sc = new Scanner(System.in);

                System.out.println("Entrer la requete avec le format : CHK login password ou Entrer FIn pour arreter.");
                requete = sc.nextLine();
                while(!requete.equalsIgnoreCase("FIN")){
                    sortie.println(requete);

                    String reponse = entree.readLine();
                    System.out.println("Reponse reçue : "+reponse);

                    requete = sc.nextLine();
                }
                sockClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
