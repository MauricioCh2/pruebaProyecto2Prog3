package labPresentation.View;

import javax.swing.*;

public class MensajesView extends JFrame {
    private JPanel pnl_principal;
    private JTextPane txP_textoMensajes;

    public MensajesView() {
        txP_textoMensajes.setEnabled(false);
    }

    public JTextPane getTxP_textoMensajes() {
        return txP_textoMensajes;
    }

    public JPanel getPnl_principal() {
        return pnl_principal;
    }

}
