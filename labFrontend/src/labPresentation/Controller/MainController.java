package labPresentation.Controller;


import Protocol.Instrumento;
import labLogic.ServiceProxy;
import labPresentation.View.Aplication;
//import labLogic.ServiceProxy;

public class MainController {
    private static Aplication app;
    private Instrumentos_Controller instrumentosController;
    private TiposInstrumentoController tiposInstrumentoController;
    private static CalibracionesController calibracionesController;

    public MainController() throws Exception {
        tiposInstrumentoController = new TiposInstrumentoController();
        instrumentosController = new Instrumentos_Controller(this);
        calibracionesController = new CalibracionesController();
        app = new Aplication();
        app.getInstrumentoView().init_controller(tiposInstrumentoController);
        app.getInstrumentosView_items().init_controller(instrumentosController);
        app.getInstrumentosView_calibs().init_controller(calibracionesController);



        instrumentosController.init_view(app.getInstrumentosView_items());//inicializa la vista de instrumentos
        tiposInstrumentoController.add_visitor(instrumentosController);
        tiposInstrumentoController.init_view(app.getInstrumentoView());

        calibracionesController.init(app.getInstrumentosView_calibs());

        ServiceProxy.instance().register();
    }
    public static void selecInsCalibrar(Instrumento ins){
        app.enableCalib();
        CalibracionesController.setInstru(ins);
        CalibracionesController.update();

    }
    public static void deselect(){
        app.deselectCalib();
    }


}
