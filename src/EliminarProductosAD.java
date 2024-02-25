import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EliminarProductosAD {
    JPanel EliminarPAD;
    private JTextField txtIDdelete;
    private JButton eliminarButton;
    private JTable table1;
    private JButton regresarButton;

    private Connection connection;


    public EliminarProductosAD() {

        //Establecer la conexion
        connection = connector.obtenerConexion();

        // Configuración de la tabla
        configureTable();
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarEliminacion();
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

    private void confirmarEliminacion() {
        try {
            // Obtener el ID ingresado
            String idInput = txtIDdelete.getText();

            // Verificar si se ingresó un ID
            if (!idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Mostrar un cuadro de diálogo de confirmación
                int confirmacion = JOptionPane.showConfirmDialog(EliminarPAD,
                        "¿Estás seguro de eliminar el producto con ID " + id + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);
                        // Limpiar los campos de entrada
                        txtIDdelete.setText("");
                if (confirmacion == JOptionPane.YES_OPTION) {
                    // Llamar al método para eliminar el producto
                    eliminarProducto(id);
                    // Limpiar los campos de entrada después de la eliminacion
                    txtIDdelete.setText("");

                    // Volver a configurar la tabla después de la eliminación
                    configureTable();
                }
            } else {
                JOptionPane.showMessageDialog(EliminarPAD, "Por favor, ingresa un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                // Limpiar los campos de entrada
                txtIDdelete.setText("");

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(EliminarPAD, "Por favor, ingresa un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
            // Limpiar los campos de entrada
            txtIDdelete.setText("");
        }
    }

    private void eliminarProducto(int id) {
        try {
            String query = "DELETE FROM productos WHERE id_producto=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(EliminarPAD,
                            "Producto eliminado exitosamente.",
                            "Eliminación Exitosa",
                            JOptionPane.INFORMATION_MESSAGE);
                            // Limpiar los campos de entrada
                            txtIDdelete.setText("");
                } else {
                    JOptionPane.showMessageDialog(EliminarPAD,
                            "No se encontró el producto con ID " + id,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                            // Limpiar los campos de entrada
                            txtIDdelete.setText("");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
