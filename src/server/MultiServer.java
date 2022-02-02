package server;


/**
 * Il server ad ogni richiesta di connessione crea un nuovo thread
 * Esempio: attivando due connessioni client - server sullo stesso PC
 *
 *
 * LATO SERVER
 * 1. Connessione riuscita dal client: /192.168.1.1 - Porta:1294
 * 2. Connessione riuscita dal client: /192.168.1.1 - Porta:1295
 *
 * LATO CLIENT
 * 1. Indirizzo del socket (processo client): /192.168.1.1 Porta - 1294
 *    Indirizzo del socket (processo server): NomeServer/192.168.1.1 Porta - 3000
 *    Al server � arrivato il seguente messaggio: Sono un Client
 *
 * 2. Indirizzo del socket (processo client): /192.168.1.1 Porta - 1295
 *    Indirizzo del socket (processo server): NomeServer/192.168.1.1 Porta - 3000
 *    Al server � arrivato il seguente messaggio: Sono un Client
 */


import client2.Client_2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class MultiServer {

    public static void main(String args[]){
        try {

            Vector<Socket> sockets = new Vector<>();
            //Vector<PrintWriter> prints = new Vector<>();
            ServerSocket server = new ServerSocket(4000);
            PrintWriter out;

            while(true) {

                System.out.println("\nMULTI-SERVER in Attesa di Connessione....");

                // 1. Attesa attiva di una connessione
                Socket socket = server.accept();
                sockets.add(socket);
//                for(int i=0; i<sockets.size(); i++){
//                    out = new PrintWriter(sockets.elementAt(i).getOutputStream());
//                    out.println(sockets);
//                    out.flush();
//                }
                //prints.add((new PrintWriter(server.accept().getOutputStream())));

                // 2. Ad ogni nuova connessione si attiva un nuovo thread
                Thread thread = new Connessione(sockets.elementAt(sockets.size()-1), sockets);
                thread.start();

            }

        }catch(IOException e) {
            System.err.println("Connessione non riuscita: " + e.getMessage());
        }
    }
}
