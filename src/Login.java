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
    boolean encontrado = false;
    public Login() {
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado = false;
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/minimarket";
                    String usuario = "root";
                    String contraseña = "";
                    conn = DriverManager.getConnection(url, usuario, contraseña);
                    String sql = "SELECT * FROM usuarios";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    String usuarioingre = UsuarioLogintextField1.getText();
                    int contrp= Integer.parseInt(ContrasenaLoginField1.getText());
                    while (rs.next()) {
                        String nom = rs.getString("nombre");
                        int contra= rs.getInt("contra");
                        if (nom.equals(usuarioingre) && contra==contrp){
                            encontrado = true;
                            String cargo= rs.getString("cargo");
                            if (cargo.equals("admin")){
                                JFrame frames = new JFrame("ADMINISTRADOR");
                                frames.setUndecorated(true);
                                frames.setContentPane(new Admin().AdminU);
                                frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frames.setSize(550,700);
                                frames.setLocationRelativeTo(null);
                                frames.setVisible(true);
                            } else if (cargo.equals("cajero")) {
                                JFrame frames = new JFrame("CAJERO");
                                frames.setUndecorated(true);
                                frames.setContentPane(new Cajero().cajerop);
                                frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frames.setSize(590,590);
                                frames.setLocationRelativeTo(null);
                                frames.setVisible(true);
                            } else if (cargo.equals("bodega")) {
                                
                            }
                        }
                    }
                    if (encontrado==false) {
                        JOptionPane.showMessageDialog(null, "Usuario incorrecto.");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
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
