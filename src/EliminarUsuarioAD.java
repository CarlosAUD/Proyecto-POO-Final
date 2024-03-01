import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarUsuarioAD {
    public JPanel eliminarusuarioad;
    public JPanel eliminarUsuarioAD;
    private JButton REGRESARButton;
    private JButton ELIMINARButton;
    private JTextField textField2;

    public EliminarUsuarioAD() {
        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new Admin().AdminU);
                Main.ventana.revalidate();
            }
        });
    }
}
