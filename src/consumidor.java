import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.*;

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
                frames.setUndecorated(true);
                frames.setContentPane(new Cajero().cajerop);
                frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frames.setSize(600, 600);
                frames.setLocationRelativeTo(null);
                frames.setVisible(true);
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
                             PDDocument document = new PDDocument()) {
                            PDPage page = new PDPage();
                            document.addPage(page);
                            PDPageContentStream contentStream = new PDPageContentStream(document, page);
                            contentStream.setFont(PDType1Font.HELVETICA, 12);
                            while (resultSet.next()) {
                                String columna1 = resultSet.getString("nombre_p");
                                String columna2 = resultSet.getString("precio");
                                String columna3 = resultSet.getString("cantidad");
                                String columna4 = resultSet.getString("sub_total");

                                contentStream.beginText();
                                contentStream.newLineAtOffset(100, 700);
                                contentStream.showText(columna1 + " - " + columna2 + " - " + columna3 + " - " + columna4);
                                contentStream.endText();
                            }
                            contentStream.close();
                            document.save("datos2.pdf");
                            System.out.println("PDF creado correctamente.");
                        } catch (Exception ex) {
                            System.err.println("Error al manipular el PDF: " + ex.getMessage());
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
