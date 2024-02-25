import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class eliminarProducto {
    JPanel eliminarJPanel;
    private JPanel insertproduct;
    private JLabel titulo;
    private JTextField txtIDproduc;
    private JButton eliminarButton;
    private JScrollPane tablemodel1;
    private JTable table1;
    private JButton regresarButton;
    private Connection connection;

    public eliminarProducto() {
        connection = connector.obtenerConexion();
        configureTable();
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatos();
            }
        });
    }
    private void eliminarDatos(){
        try {
            int idProducto = Integer.parseInt(txtIDproduc.getText());
            String deleteQuery = "DELETE FROM productos WHERE id_producto = ?";
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, String.valueOf(idProducto));

            txtIDproduc.setText("");
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                configureTable();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún registro con ese ID");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el registro: " + e.getMessage());
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
                model.addRow(new Object[]{id_producto,tipo,nombre,marca,catidad,precio });
            }
            table1.setModel(model);
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
