import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class CrearUsuarioAD {
    public JPanel crearusuarioad;
    private JTextField textField1;
    private JPasswordField contracrearAD;
    private JButton CREARButton;
    private JButton REGRESARButton;
    private JPasswordField contracrearAD2;
    private Connection connection;

    public CrearUsuarioAD() {
        connection = connector.obtenerConexion();
        CREARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new Admin().AdminU);
                Main.ventana.revalidate();
            }
        });
    }
}
