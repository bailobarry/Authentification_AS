package fr.miage.toulouse.appRepartie.clients;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class ClientCheckerUDP {
    public static void main(String[] args) {
        // Création d'un socket UDP sur un port choisi par le système
        DatagramSocket sockClient = null;
        try{
            sockClient = new DatagramSocket();
            // tampon pour recevoir les données des datagrammes UDP
            final byte[] buf = new byte[1024];

            InetAddress destination = null;
            try{
                destination = InetAddress.getByName("localhost");
            }catch(Exception e){
                throw new RuntimeException();
            }

            Scanner sc = new Scanner(System.in);

            String requete = null;
            System.out.println("Entrer la requete avec le format : CHK login password ou Entrer FIN pour arreter.");
            requete = sc.nextLine();
            while(!requete.equalsIgnoreCase("FIN")){

                byte[] donnees = requete.getBytes();

                DatagramPacket envoi = new DatagramPacket(donnees, donnees.length, destination, 28414);
                DatagramPacket entree = new DatagramPacket(buf, buf.length);

                try{
                    sockClient.send(envoi);

                    sockClient.receive(entree);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String reponse = new String(entree.getData(), 0, entree.getLength());
                System.out.println("Reponse reçue : "+reponse);

                requete = sc.nextLine();
            }
            sockClient.close();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
