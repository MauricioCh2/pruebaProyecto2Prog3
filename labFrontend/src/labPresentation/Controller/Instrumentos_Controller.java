package labPresentation.Controller;

import Protocol.IController;
import Protocol.Instrumento;
import Protocol.Protocol;
import Protocol.TipoInstrumentoObj;
import labLogic.ServiceProxy;
import labPresentation.Model.InstrumentosModel;
import labPresentation.Model.PDF;
import labPresentation.View.InstrumentosView;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Instrumentos_Controller implements IController {
    //private static InstrumentosView view;

    private  static InstrumentosModel model;
    private static InstrumentosView instrumentView;
    private static boolean EDIT;
    private static MainController mainCont;

    private static Instrumento instrumento;
    ServiceProxy localService;
    private PDF pdfO;

    public Instrumentos_Controller(MainController cont) throws ParserConfigurationException, IOException, TransformerException {
        super();
        EDIT = false;
       mainCont = cont;
        //this.instrumentView = view;

    }
    public void init_view(InstrumentosView view) throws Exception {
        this.instrumentView = view;
        this.model = new InstrumentosModel(instrumentView.getTbl_Listado_Instrumentos(), pdfO);
        localService = (ServiceProxy)ServiceProxy.instance();//especificamos que va ase un Service proxy
        ServiceProxy.instance().setTControllerInstrumento(this);
        model.updateLista();
    }
    @Override
    public void update(Object o, int pro) throws Exception {
        System.out.println("\n llegue al update ");
        if(pro == Protocol.RELOAD_INSTRUMENTO){
            model.setListaInstrumento((List<Instrumento>) o);
            cargarDatos((List<Instrumento>) o);
        }
    }


    public void cargarDatos(List<Instrumento> list) throws Exception {
        model.cargarDatos(instrumentView.getTbl_Listado_Instrumentos(),list);
    }
    public static void guardar_instrumento() {
        try {
            instrumentView.getBtn_borrar().setEnabled(false);
           // if (validar_excepciones(Integer.parseInt(instrumentView.getTxF_Maximo().getText()), Integer.parseInt(instrumentView.getTxF_Minimo().getText()), instrumentView.getTxF_Serie().getText())) {
            if (validar_excepciones(instrumentView.getTxF_Serie().getText())){
                //Instrumento(String serie, String descripcion, String tipo, int maximo, int minimo, double tolerancia )
                Instrumento instrumento = new Instrumento(instrumentView.getTxF_Serie().getText(), instrumentView.getTxF_Descripcion().getText(), String.valueOf(instrumentView.getCmB_Tipo().getSelectedItem().toString()), Integer.parseInt(instrumentView.getTxF_Maximo().getText()),
                        Integer.parseInt(instrumentView.getTxF_Minimo().getText()), Double.parseDouble(instrumentView.getTxF_Tolerancia().getText()));

                if (!EDIT) {
                    model.save(instrumento);
                    //JOptionPane.showMessageDialog(null, "Tipo de instrumento agregado");

                } else {
                    verificarTabla();
                    model.actualizar(instrumento);
                    //JOptionPane.showMessageDialog(null, "Tipo de instrumento actualizado");
                }

                limpiar_pnl_ingreso_txFields();
            }


            EDIT = false;
        }catch(Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void eliminar_elemento(String serie) throws Exception {
        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de querer borrar el instrumento? ESTO ELIMINARA LAS CALIBRACIONES Y SUS MEDICIONES ASOCIADAS",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            limpiar_pnl_ingreso_txFields();
            model.eliminar_elemento(serie);
        }


    }

    public static boolean validar_excepciones(String serie){
        int min =0;
        int max=0;
        boolean res = false;
        try {
            if (txt_field_vacio()) {
                throw new Exception("Hay campos vacios, por favor revisar");

            }
            min = Integer.parseInt(instrumentView.getTxF_Minimo().getText());
            max = Integer.parseInt(instrumentView.getTxF_Maximo().getText());
            if (min>=max) {
                throw new Exception("El valor maximo debe ser mayor al minimo");
            }

//            if (model.instrumento_existente(serie)){
//                throw new Exception("Este ID ya existe selecciona uno distinto");
//            }
            //if (instrumentView.getCmB_Tipo().getItemCount() == 0){
                //throw new Exception("No existen categorias de instrumentos");
            //}

        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true;
    }


    public static boolean txt_field_vacio(){
        return instrumentView.getTxF_Serie().getText().isEmpty() | instrumentView.getTxF_Descripcion().getText().isEmpty() | instrumentView.getTxF_Minimo().getText().isEmpty()
                | instrumentView.getTxF_Maximo().getText().isEmpty() | instrumentView.getTxF_Tolerancia().getText().isEmpty();
    }


    public static void limpiar_pnl_ingreso_txFields() throws Exception {
        EDIT = false;
        instrumentView.getTxF_Serie().setEnabled(true);
        instrumentView.getBtn_borrar().setEnabled(false);
        instrumentView.getTxF_Serie().setText("");
        instrumentView.getTxF_Descripcion().setText("");
        instrumentView.getTxF_Minimo().setText("");
        instrumentView.getTxF_Maximo().setText("");
        instrumentView.getTxF_Tolerancia().setText("");
        instrumentView.getCmB_Tipo().setSelectedIndex(0);
        MainController.deselect();
        model.updateLista();
    }

    public static void rellenar_textfields(MouseEvent e){
        instrumentView.getTxF_Serie().setEnabled(false);
        instrumentView.getBtn_borrar().setEnabled(true);
        try {
            if (instrumentView.getTbl_Listado_Instrumentos().getSelectedRow() != -1) {
                if (e.getClickCount() == 1) {
                    DefaultTableModel modelo = (DefaultTableModel) instrumentView.getTbl_Listado_Instrumentos().getModel();
                    String noSerie = (String) instrumentView.getTbl_Listado_Instrumentos().getValueAt(instrumentView.getTbl_Listado_Instrumentos().getSelectedRow(), modelo.findColumn("No. Serie"));
                    instrumentView.getTxF_Serie().setText(String.valueOf(instrumentView.getTbl_Listado_Instrumentos().getValueAt(instrumentView.getTbl_Listado_Instrumentos().getSelectedRow(), modelo.findColumn("No. Serie"))));
                    instrumentView.getTxF_Descripcion().setText(String.valueOf(instrumentView.getTbl_Listado_Instrumentos().getValueAt(instrumentView.getTbl_Listado_Instrumentos().getSelectedRow(),  modelo.findColumn("Descripcion"))));
                    instrumentView.getTxF_Minimo().setText(String.valueOf(instrumentView.getTbl_Listado_Instrumentos().getValueAt(instrumentView.getTbl_Listado_Instrumentos().getSelectedRow(),  modelo.findColumn("Minimo"))));
                    instrumentView.getTxF_Maximo().setText(String.valueOf(instrumentView.getTbl_Listado_Instrumentos().getValueAt(instrumentView.getTbl_Listado_Instrumentos().getSelectedRow(),  modelo.findColumn("Maximo"))));
                    instrumentView.getTxF_Tolerancia().setText(String.valueOf(instrumentView.getTbl_Listado_Instrumentos().getValueAt(instrumentView.getTbl_Listado_Instrumentos().getSelectedRow(),  modelo.findColumn("Tolerancia"))));
                    //instrumentView.getCmB_Tipo().setSelectedItem("");
                    instrumentView.getCmB_Tipo().setSelectedItem(model.busquedaInstrumento(noSerie).getTipo());

                }
            } else {
                //throw new Exception("Selecciona una columna primero");
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        EDIT = true;
    }

    public void agregar_categoriaCB(TipoInstrumentoObj ct){
        instrumentView.getCmB_Tipo().addItem(ct.getNombre());

    }

    public void setPDF(PDF pdf) {
        pdfO = pdf;
    }


    // classes
    public static class Btns_Instrumento implements ActionListener { //controller de botones de tipo de instrumento
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Guardar": {
                    guardar_instrumento();
                    break;
                }
                case "Borrar": {
                    try {
                        eliminar_elemento(instrumentView.getTxF_Serie().getText());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                case "Limpiar": {
                    try {
                        limpiar_pnl_ingreso_txFields();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                case "Buscar": {
                    try {
                        buscar();
                    } catch (XPathExpressionException | ParserConfigurationException | IOException | SAXException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                case "Reporte": {
                    try {
                        reporte();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                default:
            }
        }

        private void reporte() throws FileNotFoundException {
            model.generarReporte();
        }

    } //Btns_Instrumento

    public static class Tables_Instrumento implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {

        }
        @Override
        public void mouseClicked(MouseEvent e) {

            rellenar_textfields(e);
            JTable tbl = instrumentView.getTbl_Listado_Instrumentos();
            //TableModel model = (TableModel) tbl.getModel();
            DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
            String no_Serie = (String) tbl.getValueAt(tbl.getSelectedRow(), modelo.findColumn("No. Serie"));

            try {
                MainController.selecInsCalibrar(model.busquedaInstrumento(no_Serie));
            } catch (XPathExpressionException | ParserConfigurationException | IOException | SAXException ex) {
                throw new RuntimeException(ex);

            } catch (Exception ex) {
                System.out.println("Error al seleccionar calibracion: "+ ex.getMessage());
            }


        }

    }

    public JComboBox getCB_categoria(){
        return  instrumentView.getCmB_Tipo();
    }

    public static void buscar() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        try {
            if(instrumentView.getTxF_Busqueda().getText().isEmpty() ){
                throw new Exception("Digite un nombre o codigo a buscar");
            }else{
                if(instrumentView.getChB_busqueda().isSelected()){
                    if(!model.busquedaPorSerie(instrumentView.getTxF_Busqueda().getText(),instrumentView.getTbl_Listado_Instrumentos())){//este buscara por numero con un equal
                        throw new Exception("No se encontraron resultados");
                    }else{
                        instrumentView.getTxF_Busqueda().setText("");
                        EDIT = true;
                    }
                }else{
                    if(!model.busquedaPorDescripcion(instrumentView.getTxF_Busqueda().getText(),instrumentView.getTbl_Listado_Instrumentos())){ //este por destino o llegada  con un contains
                        throw new Exception("No se encontraron resultados");
                    }else{
                        instrumentView.getTxF_Busqueda().setText("");
                        EDIT = true;
                    }
                }
               // model.busquedaPorDescripcion(instrumentView.getTxF_Busqueda().getText(),instrumentView.getTbl_Listado_Instrumentos());

            }
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void verificarTabla() throws Exception{
        if (!model.elementoExistente(instrumentView.getTxF_Serie().getText(), instrumentView.getTbl_Listado_Instrumentos())) {
            limpiar_pnl_ingreso_txFields();
            throw new Exception("Este Elemento Ya No Existe");
        }

    }


}











