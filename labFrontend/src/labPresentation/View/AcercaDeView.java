package labPresentation.View;

import javax.swing.*;
import java.awt.*;

public class AcercaDeView {
    private JPanel pnl_AcercaDe;
    private JTextField joséAlbertoVindasQuirosTextField;
    private JTextField brayronLeivaSalas118750812TextField;
    private JTextField mauricioChavesChaves118660315TextField;
    private JTextField angélicaVargasArroyo402600964TextField;
    private JLabel Imagen;
    private JTextPane AcercaProyecto;

    public AcercaDeView(){
        ImageIcon imagenIcon = new ImageIcon(("/java/Icons/Icon.png"));

        // Escalar la imagen a 50x50 píxeles
        Image scaledImage = imagenIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        // Crear un JLabel con la imagen escalada
        Imagen = new JLabel(new ImageIcon(scaledImage));
        joséAlbertoVindasQuirosTextField.setEnabled(false);
    }
    public  JPanel getPanel() {
        return  pnl_AcercaDe;
    }
}
