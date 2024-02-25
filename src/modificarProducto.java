import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class modificarProducto {
    JPanel modificarJPanel;
    private JTable table1;
    private JTextField textField1;
    private JButton verificarButton;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton modificarButton;
    private Connection connection;

    public modificarProducto() {
        connection = connector.obtenerConexion();
        configureTable();
        verificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarDatos();
            }
        });
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarDatos();
            }
        });
    }
    public void cambiarDatos(){
        try {
            int idProducto = Integer.parseInt(textField1.getText());
            String tipo = textField2.getText();
            String nombre = textField3.getText();
            String marca = textField4.getText();
            int cantidad = Integer.parseInt(textField5.getText());
            double precio = Double.parseDouble(textField6.getText());

            // Validar si el ID existe en la base de datos
            String selectQuery = "SELECT * FROM productos WHERE id_producto = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setInt(1, idProducto);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // El ID existe, ahora actualizar el registro con los nuevos valores
                String updateQuery = "UPDATE productos SET tipo = ?, nombre = ?, marca = ?, cantidad = ?, precio = ? WHERE id_producto = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, tipo);
                updateStatement.setString(2, nombre);
                updateStatement.setString(3, marca);
                updateStatement.setInt(4, cantidad);
                updateStatement.setDouble(5, precio);

                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el registro");
                }
            } else {
                JOptionPane.showMessageDialog(null, "El ID no fue encontrado");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el ID: " + e.getMessage());
        }
    }

    public void modificarDatos() {
        try {
            int idProducto = Integer.parseInt(textField1.getText());
            String selectQuery = "SELECT * FROM productos WHERE id_producto = ?";
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, idProducto);

            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id_producto");
            model.addColumn("tipo");
            model.addColumn("nombre");
            model.addColumn("marca");
            model.addColumn("cantidad");
            model.addColumn("precio");
            // Añade más columnas según la estructura de tu tabla

            if (resultSet.next()) {
                // Agrega una fila al modelo con los datos del registro encontrado
                model.addRow(new Object[]{
                        resultSet.getInt("id_producto"),
                        resultSet.getString("tipo"),
                        resultSet.getString("nombre"),
                        resultSet.getString("marca"),
                        resultSet.getInt("cantidad"),
                        resultSet.getDouble("precio"),
                        // Añade más columnas según la estructura de tu tabla
                });
                JOptionPane.showMessageDialog(null, "El ID fue encontrado");
            } else {
                JOptionPane.showMessageDialog(null, "El ID no fue encontrado");
            }

            // Asigna el modelo a tu tabla
            table1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el ID: " + e.getMessage());
        }
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
                int catidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");
                model.addRow(new Object[]{id_producto, tipo, nombre, marca, catidad, precio});
            }
            table1.setModel(model);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
