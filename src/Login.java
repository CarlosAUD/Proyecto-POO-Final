import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {
    public JPanel LoginN;
    private JPasswordField ContrasenaLoginField1;
    private JTextField UsuarioLogintextField1;
    private JButton INGRESARButton;
    private JButton SALIRButton;
    private JComboBox comboBox1;
    private JLabel advertencia;
    boolean encontrado = false;
    public Login() {
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = UsuarioLogintextField1.getText();
                String contraseña = String.valueOf(ContrasenaLoginField1.getText());
                String opcionSeleccionada = (String) comboBox1.getSelectedItem();
                if (!nombre.isEmpty() && !contraseña.isEmpty()) {
                    Connection conexion = connector.obtenerConexion();
                    switch (opcionSeleccionada) {
                        case "Administrador":
                            try {
                                String sql = "SELECT * FROM usuarios WHERE nombre = '"+ nombre+"' AND contra = "+ contraseña;
                                Statement statement = conexion.createStatement();
                                ResultSet resultSet = statement.executeQuery(sql);
                                if (resultSet.next()) {
                                    Main.ventana.setContentPane(new Admin().AdminU);
                                    Main.ventana.revalidate();
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontró información del estudiante en listaestudiantes");
                                }

                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al realizar la consulta SQL");
                                exception.printStackTrace();
                            }

                            break;
                        case "Cajero":
                            try {
                                String sql = "SELECT * FROM usuarios WHERE nombre = '"+ nombre+"' AND contra = "+ contraseña;
                                Statement statement = conexion.createStatement();
                                ResultSet resultSet = statement.executeQuery(sql);
                                if (resultSet.next()) {
                                    Main.ventana.setContentPane(new Cajero().cajerop);
                                    Main.ventana.revalidate();
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontró información del estudiante en listaestudiantes");
                                }

                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al realizar la consulta SQL");
                                exception.printStackTrace();
                            }
                            break;
                        case "Bodeguero":
                            try {
                                String sql = "SELECT * FROM usuarios WHERE nombre = '"+ nombre+"' AND contra = "+ contraseña;
                                Statement statement = conexion.createStatement();
                                ResultSet resultSet = statement.executeQuery(sql);
                                if (resultSet.next()) {
                                    Main.ventana.setContentPane(new Bodega().bodegaJPanel);
                                    Main.ventana.revalidate();
                                } else {
                                    JOptionPane.showMessageDialog(null, "No se encontró información del estudiante en listaestudiantes");
                                }

                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al realizar la consulta SQL");
                                exception.printStackTrace();
                            }
                            break;
                    }
                }else{
                    advertencia.setText("Ingrese sus credenciales");
                }
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
