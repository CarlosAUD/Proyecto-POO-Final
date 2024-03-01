import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarUsuarioAD {
    public JPanel modificarusuarioad;
    public JPanel modificarUsuarioAD;
    private JTextField textField1;
    private JPasswordField contracrearAD;
    private JPasswordField contracrearAD2;
    private JButton CREARButton;
    private JButton REGRESARButton;
    private JTextField textField2;

    public ModificarUsuarioAD() {
        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new Admin().AdminU);
                Main.ventana.revalidate();
            }
        });
    }
}
