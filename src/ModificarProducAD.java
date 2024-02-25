import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificarProducAD{
    JPanel modifiproduc;
    private JLabel Titulo2;
    private JButton buscarButton;
    private JTextField txtBuscarID;
    private JTextField txtID;
    private JTextField txtTipo;
    private JTextField txtNombre;
    private JTextField txtMarca;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JTable table1;
    private JButton regresarButton;
    private JButton actualizarButton;

    private Connection connection;

    public ModificarProducAD() {

        //Establecer la conexion
        connection = connector.obtenerConexion();

        // Configuración de la tabla
        configureTable();
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();

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

    //Funcion para buscar Productos
    private void buscarProducto() {
        try {
            // Obtener el ID ingresado
            String idInput = txtBuscarID.getText();

            // Verificar si se ingresó un ID
            if (!idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (exisproducto(id)) {
                    // Obtener la información del producto por ID
                    String query = "SELECT * FROM productos WHERE id_producto=?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1,id);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            // Mostrar la información en los campos de texto
                            if (resultSet.next()) {
                                int id_producto = resultSet.getInt("id_producto");
                                String tipo = resultSet.getString("tipo");
                                String nombre = resultSet.getString("nombre");
                                String marca = resultSet.getString("marca");
                                int cantidad = resultSet.getInt("cantidad");
                                double precio = resultSet.getDouble("precio");

                                txtID.setText(String.valueOf(id_producto));
                                txtTipo.setText(tipo);
                                txtNombre.setText(nombre);
                                txtMarca.setText(marca);
                                txtCantidad.setText(String.valueOf(cantidad));
                                txtPrecio.setText(String.valueOf(precio));


                                String mensaje = "ID: " + id_producto +"\nTipo: "+ tipo +"\nNombre: " + nombre + "\nMarca : " + marca +
                                        "\nCantidad: " + cantidad+ "\nPrecio: "+precio+"$";
                                JOptionPane.showMessageDialog(this.modifiproduc, mensaje, "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.modifiproduc, "El producto con ID " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    //Verifiacion de los productos

    private boolean exisproducto(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM productos WHERE id_producto=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

    // Modificar el método actualizar Productos para usar el ID ingresado
    private void actualizarProducto() {
        try {
            // Obtener el ID ingresado
            String idInput = JOptionPane.showInputDialog(this.modifiproduc, "Ingrese el ID del empleado que desea actualizar:", "Actualizar por ID", JOptionPane.QUESTION_MESSAGE);

            // Verificar si se ingresó un ID
            if (idInput != null && !idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (exisproducto(id)) {
                    // Obtener los nuevos valores de los campos de texto
                    int id_producto = Integer.parseInt(txtID.getText());
                    String tipo = txtTipo.getText();
                    String nombre = txtNombre.getText();
                    String marca = txtMarca.getText();
                    int cantidad = Integer.parseInt(txtCantidad.getText());
                    double precio = Double.parseDouble(txtPrecio.getText());

                    // Actualizar la tabla de productos en la base de datos por ID
                    String query = "UPDATE productos SET id_producto=?, tipo=?, nombre=?, marca=?, cantidad=?, precio=? WHERE id_producto=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,id_producto);
                    preparedStatement.setString(2, tipo);
                    preparedStatement.setString(3, nombre);
                    preparedStatement.setString(4, marca);
                    preparedStatement.setInt(5,cantidad);
                    preparedStatement.setDouble(6, precio);
                    preparedStatement.setInt(7, id);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    // Limpiar los campos de entrada después de la inserción
                    txtID.setText("");
                    txtTipo.setText("");
                    txtNombre.setText("");
                    txtMarca.setText("");
                    txtCantidad.setText("");
                    txtPrecio.setText("");

                    // Recargar datos en la tabla
                    configureTable();
                } else {
                    JOptionPane.showMessageDialog(this.modifiproduc, "El empleado con ID " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
