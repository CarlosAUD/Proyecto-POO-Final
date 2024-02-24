import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bodega {
    JPanel bodegaJPanel;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;

    public Bodega() {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source == radioButton1) {
                    Main.ventana.setContentPane(new verProducto().verJPanel);
                    Main.ventana.revalidate();
                } else if (source == radioButton2) {
                } else if (source == radioButton3) {
                }
            }
        };
        radioButton1.addActionListener(listener);
        radioButton2.addActionListener(listener);
        radioButton3.addActionListener(listener);
    }
}
