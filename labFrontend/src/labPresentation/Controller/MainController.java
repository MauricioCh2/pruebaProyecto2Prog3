package labPresentation.Controller;


import Protocol.Instrumento;
import labLogic.ServiceProxy;
import labPresentation.Model.PDF;
import labPresentation.View.Aplication;

import javax.swing.*;
//import labLogic.ServiceProxy;

public class MainController {
    private static Aplication app;
    private Instrumentos_Controller instrumentosController;
    private TiposInstrumentoController tiposInstrumentoController;
    private static CalibracionesController calibracionesController;
    private MessageController messageController;
    static PDF pdf;
    public MainController() throws Exception {
        ServiceProxy.instance().register();
        ServiceProxy.instance().setController(this);
        messageController = new MessageController();
        pdf = new PDF();
        tiposInstrumentoController = new TiposInstrumentoController();
        instrumentosController = new Instrumentos_Controller(this);
        calibracionesController = new CalibracionesController();
        tiposInstrumentoController.setPDF(pdf);
        instrumentosController.setPDF(pdf);
        calibracionesController.setPDF(pdf);


        app = new Aplication();
        app.getInstrumentoView().init_controller(tiposInstrumentoController);
        app.getInstrumentosView_items().init_controller(instrumentosController);
        app.getInstrumentosView_calibs().init_controller(calibracionesController);



        instrumentosController.init_view(app.getInstrumentosView_items());//inicializa la vista de instrumentos
        tiposInstrumentoController.add_visitor(instrumentosController);
        tiposInstrumentoController.init_view(app.getInstrumentoView());
        messageController.initview(app.getMessageView());

        calibracionesController.init(app.getInstrumentosView_calibs());






    }
    public static void selecInsCalibrar(Instrumento ins) throws Exception {
        app.enableCalib();
        CalibracionesController.setInstru(ins);
        CalibracionesController.update();


    }
    public static void deselect(){
        app.deselectCalib();
    }

    public void showMessage(String mes){
        JOptionPane.showMessageDialog(app,mes);
    }
    public void showErrorMessage(String mes){
        JOptionPane.showMessageDialog(app,mes, "Error",JOptionPane.ERROR_MESSAGE);
    }

}
