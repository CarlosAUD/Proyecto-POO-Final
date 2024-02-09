import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public JPanel LoginN;
    private JPasswordField ContrasenaLoginField1;
    private JTextField UsuarioLogintextField1;
    private JButton INGRESARButton;
    private JButton SALIRButton;

    public Login() {
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frames = new JFrame("ADMINISTRADOR");
                frames.setUndecorated(true);
                frames.setContentPane(new Admin().AdminU);
                frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frames.setSize(550,700);
                frames.setLocationRelativeTo(null);
                frames.setVisible(true);
            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "¡Muchas gracias por su visita!", "Gracias por Preferirnos", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
    }
}
