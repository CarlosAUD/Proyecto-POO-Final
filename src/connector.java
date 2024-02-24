import java.sql.*;

public class connector {
    // Datos de conexión a la base de datos
    private static final String dbhost = "jdbc:mysql://ukcbxiwbpnwnjbhz:nnnad12meO4RxPBS0TVq@bgisygx1p6vq9tj7srfr-mysql.services.clever-cloud.com:3306/bgisygx1p6vq9tj7srfr";
    private static final String dbuser = "ukcbxiwbpnwnjbhz";
    private static final String dbpassword = "nnnad12meO4RxPBS0TVq";

    // Método para obtener la conexión a la base de datos
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Registro del driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer conexión
            conexion = DriverManager.getConnection(dbhost, dbuser, dbpassword);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}
