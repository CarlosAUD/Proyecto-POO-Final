import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

public class consumidor {
    JPanel consumidor;
    private JButton vistobut;
    private JButton xButton;

    public consumidor() {
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(xButton);
                frame.dispose();
                JFrame frames = new JFrame("EL MINI MINI MARKET PRO PLUS +");
                frame.setUndecorated(true);
                frame.setContentPane(new Cajero().cajerop);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600,600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        vistobut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String url = "jdbc:mysql://localhost:3306/minimarket";
                    String usuario = "root";
                    String contraseña = "";
                    try (Connection conexion = DriverManager.getConnection(url, usuario, contraseña)) {

                        String consulta = "SELECT * FROM facturas_temp";

                        try (Statement statement = conexion.createStatement();
                             ResultSet resultSet = statement.executeQuery(consulta);
                             PrintWriter writer = new PrintWriter(new FileWriter("datos.txt"))) {
                            while (resultSet.next()) {
                                String columna1 = resultSet.getString("nombre_p");
                                String columna2 = resultSet.getString("precio");
                                String columna3 = resultSet.getString("cantidad");
                                String columna4 = resultSet.getString("sub_total");
                                writer.println(columna1 + "\t" + columna2 + "\t" + columna3 + "\t" +columna4);
                            }
                            System.out.println("Datos guardados en datos.txt correctamente.");
                            try (PDDocument document = new PDDocument()) {
                                PDPage page = new PDPage();
                                document.addPage(page);

                                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                                contentStream.beginText();
                                contentStream.newLineAtOffset(100, 700);
                                try (BufferedReader reader = new BufferedReader(new FileReader("D:/csdavid/Proyecto-POO-Final/datos.txt"))) {
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        contentStream.showText(line);
                                        contentStream.newLine();
                                    }
                                }

                                contentStream.endText();
                                contentStream.close();

                                document.save("datos.pdf");
                                System.out.println("Archivo PDF creado correctamente.");
                            }

                        } catch (Exception ex) {
                            System.err.println("Error al convertir el archivo: " + ex.getMessage());
                        }

                    } catch (Exception ex) {
                        System.err.println("Error al conectar con la base de datos: " + ex.getMessage());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
