package client1;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client_1 implements ActionListener, KeyListener {
    private static final JFrame window = new JFrame("Client1");
    private final JTextField TFmessaggio;
    private static JPanel JPconversazione, JPchats;
    private static JLabel LBmessaggio_client;
    private static final Dimension labelsDimension = new Dimension(350,20);

    //CONNECTS ADN SENDS/RECEIVES FILES
    private static  PrintWriter out;
    private static Socket sock;
    public static void connect() throws IOException {
        //String addr = "192.168.1.131";
        InetAddress addr = InetAddress.getLocalHost();
        int porta = 4000;
        sock = new Socket(addr, porta);
        System.out.println("Connessione avvenuta");
        out = new PrintWriter(sock.getOutputStream());

        Scanner scan = new Scanner(sock.getInputStream());
        while (scan.hasNextLine()){
            String serverChoice = scan.nextLine();
            System.out.println(serverChoice);
            addServerMessage(serverChoice);
        }
    }

    public Client_1() {

        window.setTitle("Client 1");
        window.setSize(400,500);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        Container c = window.getContentPane();

        JPconversazione = new JPanel();
        JPconversazione.setLayout(new BoxLayout(JPconversazione, BoxLayout.PAGE_AXIS));

        //scroll pane
        JScrollPane scroll = new JScrollPane(JPconversazione, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        c.add(scroll);

        JPanel JPmessaggio = new JPanel();
        JPmessaggio.setPreferredSize(new Dimension(400,50));

        JPmessaggio.setBorder(border);
        c.add(JPmessaggio, BorderLayout.SOUTH);

        TFmessaggio = new JTextField();
        TFmessaggio.setPreferredSize(new Dimension(300,20));
        JPmessaggio.add(TFmessaggio);

        JButton BTinvia = new JButton("=>");
        BTinvia.addActionListener(this);
        BTinvia.setFocusable(false);
        JPmessaggio.add(BTinvia);

//        JPchats = new JPanel();
//        JPchats.add(new JButton("NOME COGNOME"));
//        JScrollPane scrollChat = new JScrollPane(JPchats, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        c.add(scrollChat, BorderLayout.WEST);
//
//        JOptionPane chats = new JOptionPane();
//        chats.add(new JLabel("global"));
//        chats.add(new JLabel("Coso 1"));
//        JPchats.add(chats);

        window.setVisible(true);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.setLocationRelativeTo(null);
        window.addKeyListener(this);
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        //CATCHES THE WINDOW CLOSING
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (JOptionPane.showConfirmDialog(window,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }

                try {
                    sock.close();
                    out.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        addClientMessage();
    }


    public void addClientMessage(){
        if(TFmessaggio.getText().isBlank()){
            System.out.println("empty message");
        }
        else{
            System.out.println("Tu: "+TFmessaggio.getText());
            out.println(TFmessaggio.getText());
            out.flush();
            LBmessaggio_client = new JLabel("Tu: "+TFmessaggio.getText().toLowerCase());
        }
        TFmessaggio.setText("");
        LBmessaggio_client.setPreferredSize(labelsDimension);
        LBmessaggio_client.setHorizontalAlignment(SwingConstants.RIGHT);
        JPconversazione.add(LBmessaggio_client);

        SwingUtilities.updateComponentTreeUI(JPconversazione);
    }

    public static void addServerMessage(String message){
        System.out.println("richiamata");
        JLabel LBmessaggio_server = new JLabel(message);
        if(LBmessaggio_server.getText().isBlank()){
            System.out.println("empty message");
        }
        else{
            LBmessaggio_server = new JLabel(LBmessaggio_server.getText().toLowerCase());
            System.out.println(LBmessaggio_server);
        }
        LBmessaggio_server.setPreferredSize(labelsDimension);
        LBmessaggio_server.setHorizontalAlignment(SwingConstants.RIGHT);
        JPconversazione.add(LBmessaggio_server);

        SwingUtilities.updateComponentTreeUI(JPconversazione);
    }

    public static void main(String[] args) throws IOException {
        new Client_1();
        connect();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
