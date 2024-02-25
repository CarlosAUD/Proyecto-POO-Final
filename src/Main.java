import javax.swing.*;

public class Main {
    static JFrame ventana = new JFrame("Gestion de Proyectos");
    public static void main(String[] args) {
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(new eliminarProducto().eliminarJPanel);
        ventana.pack();
        ventana.setSize(1000, 800);
        ventana.setVisible(true);
    }
}