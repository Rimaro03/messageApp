package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Connessione extends Thread  {
    private Socket sock;
    private Vector<Socket> sockets;

    public Connessione(Socket s, Vector<Socket> ss){
        this.sock = s;
        this.sockets = ss;
    }

    @Override
    public void run() {
        super.run();
        Scanner scan = null;
        PrintWriter out = null;

        try {
            scan = new Scanner(sock.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        String messaggio;
        //ECHO
        while(scan.hasNextLine()){
            messaggio = scan.nextLine();
            for(int i=0; i<sockets.size(); i++){
                if(sockets.elementAt(i) != sock){
                    try {
                        out = new PrintWriter(sockets.elementAt(i).getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out.println("client"+i+": "+messaggio);
                    out.flush();
                }
            }
        }
    }
}

