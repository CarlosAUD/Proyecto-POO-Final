import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("EL MINI MINI MARKET PRO PLUS +");
        frame.dispose();
        frame.setUndecorated(true);
        frame.setContentPane(new Login().LoginN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}