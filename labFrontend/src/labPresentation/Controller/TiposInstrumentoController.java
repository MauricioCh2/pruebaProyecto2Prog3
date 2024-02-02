package labPresentation.Controller;


import Protocol.IController;
import Protocol.TipoInstrumentoObj;
import labLogic.ServiceProxy;
import labPresentation.Model.TiposInstrumentosModel;
import labPresentation.View.TipoInstrumentoView;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

public class TiposInstrumentoController implements IController {
    private static TiposInstrumentosModel tInstrumentosModel;
    private static JTextField txF_codigo;
    private static JTextField txF_nombre;
    private static JTextField txF_unidad;
    private static JTextField txF_busqueda;
    private static JButton btn_borrar;
    private static JTable tbl_listadoTipos;
    private static boolean EDIT;
    //private static List<TipoInstrumentoObj> listaInstrumentos;
    private static TipoInstrumentoView tpInst;
    private static JCheckBox chB_busqueda;
    private static Instrumentos_Controller instrumentos_controller; //THIS
    ServiceProxy localService;
    //Constructor------------------------------------------------------------------------------------------------------
    public TiposInstrumentoController() throws ParserConfigurationException, IOException, TransformerException {
        super();

        EDIT = false;

    }
    public void init_view(TipoInstrumentoView view) throws Exception {
         tpInst = view;
         tbl_listadoTipos = tpInst.getTbl_ListadoTipos();
         btn_borrar = tpInst.getBtn_borrar();
         txF_codigo = tpInst.getTxF_Codigo();
         txF_nombre = tpInst.getTxF_Nombre();
         txF_unidad = tpInst.getTxF_Unidad();
         txF_busqueda = tpInst.getTxF_buscarNombre();
         chB_busqueda = tpInst.getChB_busqueda();

        localService = (ServiceProxy)ServiceProxy.instance();//especificamos que va ase un Service proxy
        //localService.setTipoinscontroller(this);

        tInstrumentosModel = new TiposInstrumentosModel(tpInst.getTbl_ListadoTipos(), instrumentos_controller.getCB_categoria());
        //listaInstrumentos = tInstrumentosModel.getListaInstrumentos();

        ServiceProxy.instance().setTController(this);
        tInstrumentosModel.inicializar_lista();
        //tInstrumentosModel.cargarDatos(tpInst.getTbl_ListadoTipos(),instrumentos_controller.getCB_categoria());



        //this.instrumentos_controller = visitor; //THIS
    }

    public void cargar_datos(List<TipoInstrumentoObj> list) throws Exception {
        tInstrumentosModel.cargarDatos(tpInst.getTbl_ListadoTipos(),list, null);
    }

    @Override
    public void update() {
        System.out.println("\n llegue al update ");
        //aqui llamamos al commit o algo asi
        //la cosa es que le cvaiga encima a la lista y la actualice
    }

    public void iniciarlizar_lista_tipos_instrumento(){

    }

    //Clases anidadas--------------------------------------------------------------------------------------------------
    public static class Btns_TipoInstrumento implements ActionListener { //controller de botones de tipo de instrumento
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){
                case "Guardar":{guardar(); break;}
                case "Borrar":{borrar(); break;}
                case "Limpiar":{limpiar(); break;}
            }

        }
    }
    public static class Tbl_tabla implements MouseListener { //se tiene que implementar todo aunque no se utilice en el caso del mouseListener
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            //InstrumentoObj =mouseEvent.getO
            rellenearTextFields(mouseEvent);
        }
        @Override
        public void mousePressed(MouseEvent mouseEvent) {}
        @Override
        public void mouseReleased(MouseEvent mouseEvent) {}
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}
        @Override
        public void mouseExited(MouseEvent mouseEvent) {}

    }
    public static class Busqueda implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switch(actionEvent.getActionCommand()){
                case "Reporte":{reporte(); break;}
                case "Buscar":{buscar(); break;}
                case "Busqueda por codigo":{changeText(); break;}
            }
        }

    }
    //Metodos----------------------------------------------------------------------------------------------------------
    static private void borrar (){

        int respuesta = JOptionPane.showConfirmDialog(
                null,
                "¿Está seguro de querer borrar el tipo?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            int valFil = tbl_listadoTipos.getSelectedRow();//obtiene el valor de la fila
            Object objCod = tbl_listadoTipos.getValueAt(valFil, 0); //obtiene el codigo en forma de Object
            String cod = objCod.toString(); // lo convertimos a string
            tInstrumentosModel.eliminar(cod, valFil); //elimina de la lista y de la tabla
            //reseteamos GUI
            resetGui();
        }
    }
    static private void guardar (){
         String str_forUptade = "";// para enviar a los combo box el texto a actualizar
        try {
            if(isTxFEmpty()){
                throw new Exception("Hay campos vacios, por favor revisar");
            }else{
                TipoInstrumentoObj instrumento = new TipoInstrumentoObj(txF_codigo.getText(), txF_nombre.getText(), txF_unidad.getText());
                if(EDIT){
                    str_forUptade = instrumento.getNombre();
                    if (tInstrumentosModel.update(instrumento)) { //guarda elemento en la lista y en la tabla
                        JOptionPane.showMessageDialog(null, "El tipo de instrumento a sido actualizado correctamente");
                        instrumentos_controller.agregar_categoriaCB(instrumento);
                        resetGui();
                    }else {
                        throw new Exception("a habido un error");
                    }
                } else {
                    if (tInstrumentosModel.save(instrumento)) { //guarda elemento en la lista y en la tabla
                        JOptionPane.showMessageDialog(null, "Tipo de instrumento agregado");
                        instrumentos_controller.agregar_categoriaCB(instrumento);//THIS
                        resetGui();
                    }
                    else {
                        throw new Exception("a habido un error");
                    }
                }
            }
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    static private void limpiar(){
        txF_nombre.setText("");
        txF_unidad.setText("");
        if (!EDIT){
            txF_codigo.setText("");
            btn_borrar.setEnabled(false);
            txF_unidad.setEnabled(true);
        }
    }
    static private  void rellenearTextFields(MouseEvent e){
        EDIT = true;
        txF_codigo.setEnabled(false);
        btn_borrar.setEnabled(true);
        try {
            if (tbl_listadoTipos.getSelectedRow() != -1) {
                if (e.getClickCount() == 1) {
                    DefaultTableModel modelo = (DefaultTableModel) tbl_listadoTipos.getModel();
                    txF_codigo.setText(String.valueOf(tbl_listadoTipos.getValueAt(tbl_listadoTipos.getSelectedRow(), modelo.findColumn("Codigo"))));
                    txF_nombre.setText(String.valueOf(tbl_listadoTipos.getValueAt(tbl_listadoTipos.getSelectedRow(), modelo.findColumn("Nombre"))));
                    txF_unidad.setText(String.valueOf(tbl_listadoTipos.getValueAt(tbl_listadoTipos.getSelectedRow(), modelo.findColumn("Unidad"))));

                }
            } else {
                throw new Exception("Seleccion una columna primero");
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    static private void buscar(){
        try {
            if(txF_busqueda.getText().isEmpty() ){
                throw new Exception("Digite un nombre o codigo a buscar");
            }else{
                if(chB_busqueda.isSelected()){
                    if(!tInstrumentosModel.busquedaPorCodigo(txF_busqueda.getText(),tbl_listadoTipos)){//este buscara por numero con un equal
                        throw new Exception("No se encontraron resultados");
                    }else{
                        txF_busqueda.setText("");
                        EDIT = true;
                    }
                }else{
                    if(!tInstrumentosModel.busquedaPorNombre(txF_busqueda.getText(),tbl_listadoTipos)){ //este por destino o llegada  con un contains
                        throw new Exception("No se encontraron resultados");
                    }else{
                        txF_busqueda.setText("");
                        EDIT = true;
                    }
                }
                //tInstrumentosModel.busquedaPorNombre(txF_busqueda.getText(),tbl_listadoTipos);

            }
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private static void changeText() {
        if (tpInst.getChB_busqueda().isSelected()) {
            tpInst.getTx_busqueda().setText("Codigo");
        }else{
            tpInst.getTx_busqueda().setText("Nombre");
        }
    }
    private static boolean isTxFEmpty() {
        Border lineBorder = new MatteBorder(0, 0, 1, 0, Color.RED);
        Border emptyBorder = new EmptyBorder(0,0,0,0);
        Border border = new CompoundBorder(lineBorder, emptyBorder);
//
        boolean isEmpty = false;

        if(tpInst.getTxF_Codigo().getText().isEmpty()){
            tpInst.getTx_Codigo().setBorder(border);
            isEmpty = true;
        }

        if(tpInst.getTxF_Nombre().getText().isEmpty()){
            tpInst.getTx_Nombre().setBorder(border);
            isEmpty = true;
        }

        if(tpInst.getTxF_Unidad().getText().isEmpty()){
            tpInst.getTx_Unidad().setBorder(border);
            isEmpty = true;
        }


        return isEmpty;
    }
    private static void desmarcar(){
        tpInst.getTx_Nombre().setBorder(null);
        tpInst.getTx_Unidad().setBorder(null);
        tpInst.getTx_Codigo().setBorder(null);
        tpInst.getTx_busqueda().setBorder(null);


    }

    static private void reporte(){
        tInstrumentosModel.generarReporteGeneral();
    }

    static private void resetGui(){ //limpia los textField y activa y desactiva lo necesario
        limpiar();
        desmarcar();
        txF_codigo.setEnabled(true);
        txF_codigo.setText("");
        btn_borrar.setEnabled(false);
        EDIT = false;
    }
    public void add_visitor(Instrumentos_Controller ctl){
        this.instrumentos_controller = ctl; //THIS
    }
}
