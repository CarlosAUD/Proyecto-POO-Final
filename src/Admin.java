import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin {
    public JPanel AdminU;
    private JButton REGRESARButton;
    private JButton CREARUButton;
    private JButton ELIMINARUButton;
    private JButton MODIFICARUButton;
    private JButton MODIFICARPButton1;
    private JButton CREARPButton1;
    private JButton ELIMINARPButton1;
    private JButton CREARVButton2;
    private JButton MODIFICARVButton2;
    private JButton ELIMINARVButton2;

    private Connection connection;

    public Admin() {

        // Conexion a la base de datos
        Connection conexion = connector.obtenerConexion();
        REGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new Login().LoginN);
                Main.ventana.revalidate();
            }
        });
        CREARPButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new InsertarProductAD().insertproduct);
                Main.ventana.revalidate();
            }
        });
        MODIFICARPButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new ModificarProducAD().modifiproduc);
                Main.ventana.revalidate();
            }
        });
        ELIMINARPButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new EliminarProductosAD().EliminarPAD);
                Main.ventana.revalidate();
            }
        });
        CREARUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new CrearUsuarioAD().crearusuarioad);
                Main.ventana.revalidate();
            }
        });
        MODIFICARUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new ModificarUsuarioAD().modificarUsuarioAD);
                Main.ventana.revalidate();
            }
        });
        ELIMINARUButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new EliminarUsuarioAD().eliminarUsuarioAD);
                Main.ventana.revalidate();
            }
        });
    }

}
