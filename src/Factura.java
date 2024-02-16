import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Factura {
    JPanel factura;
    private JTextField nombreclitext;
    private JTextField cedulatext;
    private JTextField dirretext;
    private JTextField telotext;
    private JButton confirmarButton;
    private JButton cancelarButton;

    public Factura() {
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(cancelarButton);
                frame.dispose();
                JFrame frames = new JFrame("EL MINI MINI MARKET PRO PLUS +");
                frame.setUndecorated(true);
                frame.setContentPane(new Cajero().cajerop);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
