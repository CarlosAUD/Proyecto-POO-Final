import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin {
    public JPanel AdminU;
    private JButton REGRESARButton;
    private JButton CREARUButton;
    private JButton ELIMINARUButton;
    private JButton MODIFICARUButton;
    private JButton MODIFICARPButton1;
    private JButton CREARPButton1;
    private JButton ELIMINARPButton1;
    private JButton CREARVButton2;
    private JButton MODIFICARVButton2;
    private JButton ELIMINARVButton2;

    public Admin() {
        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(REGRESARButton);
                frame.dispose();
                JFrame frames = new JFrame("EL MINI MINI MARKET PRO PLUS +");
                frame.setUndecorated(true);
                frame.setContentPane(new Login().LoginN);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,700);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
