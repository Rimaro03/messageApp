import javax.swing.*;
import java.awt.*;

public class key extends JFrame {
    public key(){
        JPanel p = new JPanel();
        JLabel lbl;

        Dimension d = new Dimension(300,20);
        for(int i=0; i<10; i++) {
            lbl = new JLabel("Prodotto n. " + i);
            lbl.setPreferredSize(d);
            p.add(lbl);
        }
        JScrollPane scroll = new JScrollPane(p);

        Container c = getContentPane();
        c.add(scroll);

        setVisible(true);
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new key();
    }
}
