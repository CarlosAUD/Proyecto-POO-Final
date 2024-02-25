import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class verProducto {
    JPanel verJPanel;
    private JTable table1;
    private JComboBox<String> comboBox1;
    private JButton button1;
    private JComboBox comboBox2;
    private JComboBox<String> comboBox3;
    private JTextField textField1;
    private JButton buscarButton;
    private JLabel advertencia;


    public verProducto() {
        Connection conexion = connector.obtenerConexion();
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM productos");
            Set<String> uniqueProductNames = new HashSet<>();
            while (rs.next()) {
                String productName = rs.getString("marca");
                uniqueProductNames.add(productName);
            }
            for (String productName : uniqueProductNames) {
                comboBox1.addItem(productName);
            }
            comboBox1.addActionListener(e -> {
                String selectedProductName = (String) comboBox1.getSelectedItem();
                try {
                    Statement newStatement = conexion.createStatement();
                    ResultSet newRs = newStatement.executeQuery("SELECT * FROM productos WHERE marca = '" + selectedProductName + "'");

                    // Definir los nombres de las columnas manualmente
                    String[] columnNames = {"id_producto", "Tipo", "Nombre","Marca", "Cantidad", "Precio"};

                    // Crear un nuevo modelo de tabla con los nombres de las columnas definidos
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    while (newRs.next()) {
                        Object[] rowData = new Object[columnNames.length];
                        for (int i = 0; i < columnNames.length; i++) {
                            rowData[i] = newRs.getObject(i + 1);
                        }
                        model.addRow(rowData);
                    }
                    table1.setModel(model);
                    newRs.close();
                    newStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM productos");
            Set<String> uniqueProductNames = new HashSet<>();
            while (rs.next()) {
                String productName = rs.getString("tipo");
                uniqueProductNames.add(productName);
            }
            for (String productName : uniqueProductNames) {
                comboBox2.addItem(productName);
            }
            comboBox2.addActionListener(e -> {
                String selectedProductName = (String) comboBox2.getSelectedItem();
                try {
                    Statement newStatement = conexion.createStatement();
                    ResultSet newRs = newStatement.executeQuery("SELECT * FROM productos WHERE tipo = '" + selectedProductName + "'");
                    DefaultTableModel model = new DefaultTableModel();
                    ResultSetMetaData metaData = newRs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        model.addColumn(metaData.getColumnName(i));
                    }
                    while (newRs.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = newRs.getObject(i);
                        }
                        model.addRow(rowData);
                    }
                    table1.setModel(model);
                    newRs.close();
                    newStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conexion = null;
                try {
                    conexion = connector.obtenerConexion();
                    Statement statement = conexion.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM productos");
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    DefaultTableModel model = new DefaultTableModel();

                    // Agregar nombres de columnas al modelo de tabla
                    for (int i = 1; i <= columnCount; i++) {
                        model.addColumn(metaData.getColumnName(i));
                    }

                    // Agregar filas de datos al modelo de tabla
                    while (rs.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = rs.getObject(i);
                        }
                        model.addRow(rowData);
                    }

                    // Establecer el modelo de tabla con nombres de columnas y filas de datos
                    table1.setModel(model);

                    rs.close();
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    if (conexion != null) {
                        try {
                            conexion.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField1.getText();
                String opcionSeleccionada = (String) comboBox3.getSelectedItem();
                if (!nombre.isEmpty()) {
                    Connection conexion = connector.obtenerConexion();
                    switch (opcionSeleccionada) {
                        case "id_producto":
                            try {
                            String sql = "SELECT * FROM productos WHERE id_producto = ?";

                            PreparedStatement statement = conexion.prepareStatement(sql);
                            statement.setString(1, nombre);
                            ResultSet resultSet = statement.executeQuery();
                            DefaultTableModel model = new DefaultTableModel();
                            ResultSetMetaData metaData = resultSet.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                model.addColumn(metaData.getColumnName(i));
                            }
                            while (resultSet.next()) {
                                Object[] rowData = new Object[columnCount];
                                for (int i = 1; i <= columnCount; i++) {
                                    rowData[i - 1] = resultSet.getObject(i);
                                }
                                model.addRow(rowData);
                            }
                            table1.setModel(model);
                            resultSet.close();
                            statement.close();
                        } catch (SQLException exception) {
                            JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                            exception.printStackTrace();
                        }

                            break;
                        case "tipo":
                            try {
                                String sql = "SELECT * FROM productos WHERE tipo = ?";
                                PreparedStatement statement = conexion.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "nombre":
                            try {
                                String sql = "SELECT * FROM productos WHERE nombre = ?";
                                PreparedStatement statement = conexion.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "marca":
                            try {
                                String sql = "SELECT * FROM productos WHERE marca = ?";
                                PreparedStatement statement = conexion.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "cantidad":
                            try {
                                String sql = "SELECT * FROM productos WHERE cantidad = ?";
                                PreparedStatement statement = conexion.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "precio":
                            try {
                                String sql = "SELECT * FROM productos WHERE precio = ?";
                                PreparedStatement statement = conexion.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                    }
                }else{
                    advertencia.setText("Ingrese sus credenciales");
                }
            }
        });
    }
}

