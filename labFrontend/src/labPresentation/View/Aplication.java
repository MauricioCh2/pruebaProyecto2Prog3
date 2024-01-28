package labPresentation.View;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;

public class Aplication extends JFrame{
    private JPanel pnl_Aplication;
    private JTabbedPane tbbP_Eleccion;
    private JPanel pnl_tiposDeInstrumentos;
    private JPanel pnl_instrumentos;
    private JPanel pnl_Calibraciones;
    private InstrumentosView instrumentosView_items;
    private TipoInstrumentoView instrumentoView;
    private CalibracionesView instrumentosView_calibs;
    private AcercaDeView acercadeView;
    public  Aplication() throws ParserConfigurationException, IOException, TransformerException {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {

        };

        //Seteos basicos------------------------------------------------------------------------------------
        setContentPane(pnl_Aplication);// pone el panel prncipal en la ventana
        this.setTitle("SILAB: Sistema de Laboratirio industrial");
        this.setSize(900, 450);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(900, 450));
        setLocationRelativeTo(null);//lo pone en medio

        //Seteos de cada tabbed Pane-
        this.instrumentoView = new TipoInstrumentoView();//crea el tipos de instrumento
        tbbP_Eleccion.setComponentAt(0,instrumentoView.getPnl_tiposIstrumentos());

        this.instrumentosView_items = new InstrumentosView(); // creal el instrumentos
        tbbP_Eleccion.setComponentAt(1, instrumentosView_items.getPnl_istrumentos_items());

        tbbP_Eleccion.setEnabledAt(2, false);
        this.instrumentosView_calibs = new CalibracionesView(); // creal el calibraciones
        tbbP_Eleccion.setComponentAt(2, instrumentosView_calibs.getPnl_calibraciones());

        this.acercadeView = new AcercaDeView();
        tbbP_Eleccion.setComponentAt(3,acercadeView.getPanel());

    }
    public void configurarVentana(JPanel panel, int width, int heigth){
        setContentPane(panel);//añadimos el panel
        this.setSize(width, heigth);
        this.setVisible(true); //lo hacemos visible
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void enableCalib(){
        tbbP_Eleccion.setEnabledAt(2,true);
    }
    public void deselectCalib(){
        tbbP_Eleccion.setEnabledAt(2,false);
    }


    public InstrumentosView getInstrumentosView_items() {
        return instrumentosView_items;
    }

    public TipoInstrumentoView getInstrumentoView() {
        return instrumentoView;
    }

    public CalibracionesView getInstrumentosView_calibs() {
        return instrumentosView_calibs;
    }


}