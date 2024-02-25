import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class InsertarProductAD {
    JPanel insertproduct;
    private JTextField txtIDproduc;
    private JTextField txtTipo;
    private JTextField txtNombrepro;
    private JTextField txtMarca;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JButton agregarButton;
    private JTable table1;
    private JScrollPane tablemodel1;
    private JButton regresarButton;
    private JLabel titulo;

    private Connection connection;

    public InsertarProductAD() {

        //Establecer la conexion
        connection = connector.obtenerConexion();

        // Configuración de la tabla
        configureTable();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposLlenos()) {
                    agregarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new Admin().AdminU);
                Main.ventana.revalidate();
            }
        });
    }

    private void configureTable() {
        try {
            // Obtener datos de la base de datos y configurar la tabla
            String query = "SELECT * FROM productos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id_producto");
            model.addColumn("tipo");
            model.addColumn("nombre");
            model.addColumn("marca");
            model.addColumn("cantidad");
            model.addColumn("precio");

            // Llenar el modelo con los datos de la base de datos
            while (resultSet.next()) {
                int id_producto = resultSet.getInt("id_producto");
                String tipo = resultSet.getString("tipo");
                String nombre = resultSet.getString("nombre");
                String marca = resultSet.getString("marca");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");
                model.addRow(new Object[]{id_producto,tipo,nombre,marca,cantidad,precio });
            }

            // Configurar la tabla con el modelo
            table1.setModel(model);

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void agregarDatos() {
        try {
            // Obtener los valores ingresados por el usuario
            int id_producto = Integer.parseInt(txtIDproduc.getText());
            String tipo = txtTipo.getText();
            String nombre = txtNombrepro.getText();
            String marca = txtMarca.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());



            // Insertar datos en la base de datos
            String insertQuery = "INSERT INTO productos (id_producto,tipo,nombre,marca,cantidad,precio) VALUES (?, ?, ?,?,?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1,id_producto);
            insertStatement.setString(2, tipo);
            insertStatement.setString(3, nombre);
            insertStatement.setString(4, marca);
            insertStatement.setInt(5,cantidad);
            insertStatement.setDouble(6, precio);
            insertStatement.executeUpdate();

            // Actualizar la tabla
            configureTable();

            // Limpiar los campos de entrada después de la inserción
            txtIDproduc.setText("");
            txtTipo.setText("");
            txtNombrepro.setText("");
            txtMarca.setText("");
            txtCantidad.setText("");
            txtPrecio.setText("");

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Datos agregados correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar recursos
            insertStatement.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Verifiacion de que todos los campos esten llenos para agregar un producto
    private boolean camposLlenos() {
        return !txtIDproduc.getText().isEmpty() && !txtTipo.getText().isEmpty() && !txtNombrepro.getText().isEmpty()
                && !txtMarca.getText().isEmpty() && !txtCantidad.getText().isEmpty() && !txtPrecio.getText().isEmpty();
    }
}
