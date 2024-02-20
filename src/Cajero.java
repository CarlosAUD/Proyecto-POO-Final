import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.format.DecimalStyle;

public class Cajero {
    JPanel cajerop;
    private JButton volverButton;
    private JTextField ingreprodu;
    private JButton buscarButton;
    private JTextField nametext;
    private JTextField preciotext;
    private JTextField cantitext;
    private JButton butonmas;
    private JButton butonmenos;
    private JButton venderButton;
    private JButton c_FinalButton;
    private JButton facturaConDatosButton;
    boolean encontrado = false;
    String nombre = null;
    int stock = 0;
    double preci = 0;
    public static int contador = 0;
    public Cajero() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado = false;
                cantitext.setText("0");
                nametext.setText("");
                preciotext.setText("");
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/minimarket";
                    String usuario = "root";
                    String contraseña = "";
                    conn = DriverManager.getConnection(url, usuario, contraseña);
                    String sql = "SELECT * FROM productos";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    String codigoIngresado = ingreprodu.getText();
                    while (rs.next()) {
                        String codigo = rs.getString("codigo");
                        if (codigo.equals(codigoIngresado)) {
                            encontrado = true;
                            nombre=rs.getString("nombre_produc");
                            stock=rs.getInt("stock");
                            preci= rs.getDouble("precio");
                        }
                    }
                    if (encontrado) {
                        nametext.setText(nombre);
                        preciotext.setText(String.valueOf(preci));
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                }
            }
        });
        butonmas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidad=Integer.parseInt(cantitext.getText());
                if (cantidad<stock){
                    cantitext.setText(Integer.toString(cantidad+1));
                }else{
                    JOptionPane.showMessageDialog(null, "Cantidad no disponible.");
                    cantitext.setText(Integer.toString(cantidad));
                }
            }
        });
        butonmenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidad=Integer.parseInt(cantitext.getText());
                if (cantidad>0){
                    cantitext.setText(Integer.toString(cantidad-1));
                }else{
                    cantitext.setText(Integer.toString(cantidad));
                }
            }
        });
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/minimarket";
                    String usuario = "root";
                    String contraseña = "";
                    conn = DriverManager.getConnection(url, usuario, contraseña);
                    String sql = "INSERT INTO facturas_temp (nombre_p,precio,cantidad,sub_total) VALUES (?,?,?,?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1,nametext.getText());
                    stmt.setDouble(2, Double.parseDouble(preciotext.getText()));
                    stmt.setInt(3,Integer.parseInt(cantitext.getText()));
                    stmt.setDouble(4,Integer.parseInt(cantitext.getText())*Double.parseDouble(preciotext.getText()));
                    int filasAfectadas = stmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Datos insertados correctamente.");
                    } else {
                        System.out.println("No se pudo insertar los datos.");
                    }
                    String sql2 = "UPDATE productos SET stock = ? WHERE codigo = ?";
                    stmt = conn.prepareStatement(sql2);
                    stmt.setInt(1,stock-Integer.parseInt(cantitext.getText()));
                    stmt.setInt(2,Integer.parseInt(ingreprodu.getText()));
                    int filasAfecta = stmt.executeUpdate();
                    if (filasAfecta > 0) {
                        System.out.println("Valor modificado correctamente.");
                    } else {
                        System.out.println("No se pudo modificar el valor.");
                    }
                    ingreprodu.setText("");
                    cantitext.setText("0");
                    nametext.setText("");
                    preciotext.setText("");
                    contador=1;
                } catch (SQLException | ClassNotFoundException ex) {
                    System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                }
            }
        });
        facturaConDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contador==1){
                    JFrame frames = new JFrame("Generar facturas");
                    frames.dispose();
                    frames.setUndecorated(true);
                    frames.setContentPane(new Factura().factura);
                    frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frames.setSize(550,700);
                    frames.setLocationRelativeTo(null);
                    frames.setVisible(true);
                }
            }
        });
        c_FinalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contador==1){
                    JFrame frames = new JFrame("Generar facturas");
                    frames.dispose();
                    frames.setUndecorated(true);
                    frames.setContentPane(new consumidor().consumidor);
                    frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frames.setSize(550,700);
                    frames.setLocationRelativeTo(null);
                    frames.setVisible(true);
                }
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(volverButton);
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
