package labPresentation.Model;

import Protocol.TipoInstrumentoObj;
import Protocol.UnidadMedida;
import labLogic.ServiceProxy;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TiposInstrumentosModel {
    private List<TipoInstrumentoObj> listaInstrumentos;
    private List<UnidadMedida> listaUnidades;
    private TipoInstrumentoObj current;
    private JTable tbl_tiposInst;
    private JComboBox cmB_tiposIns;
    private JComboBox cmB_UnidadMed;
    private DOM_tiposInstrumento dom;
    private PDF reporte;

    //Metodos----------------------------------------------------------------------------------------------------------
    public TiposInstrumentosModel(JTable table, JComboBox comb, JComboBox combUnidad) throws ParserConfigurationException, IOException, TransformerException {
        listaInstrumentos = new ArrayList<>();
        cmB_tiposIns = comb;
        cmB_UnidadMed = combUnidad;
        tbl_tiposInst = table;
        dom = new DOM_tiposInstrumento();
        reporte = new PDF("tipos de instrumentos", dom);
    }
    public void save(TipoInstrumentoObj ins) throws Exception {
        DefaultTableModel model = (DefaultTableModel) tbl_tiposInst.getModel();
       // boolean guardado = true;
                ins.setUnidad(getUnidadNom(ins.getUnidadId()));
                ServiceProxy.instance().create(ins);
                ServiceProxy.instance().send_tipos_instrumento(ins);
                Object[] newRow = {ins.getCodigo(),ins.getNombre(),ins.getUnidad()};
                model.addRow(newRow);
                updateLista();
       // return guardado;
    }


    public void eliminar(String ins, int fila){
        try{
            ServiceProxy.instance().delete(ins);
            DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
            listaInstrumentos.remove(ins);//elimina elemento de la lista
            modelo.removeRow(fila);         //elimina elemento de la tabla
            updateLista();
            //dom.eliminarTipo(ins);
            //dom.cargaTiposATable(tbl_tiposInst, cmB_tiposIns);



        } catch (Exception e){
//
        }

    }

    public boolean update (TipoInstrumentoObj ins) throws Exception {
        boolean re;

                ServiceProxy.instance().update(ins);
                updateLista();
        re = true;

        return re;
    }
    private boolean createTipoXML(TipoInstrumentoObj tIns){
        return dom.addTipoInstrumento(tIns);
    }

    public List<TipoInstrumentoObj> getListaInstrumentos() {
        return listaInstrumentos;
    }

    public void setListaInstrumentos(List<TipoInstrumentoObj> listaInstrumentos) {this.listaInstrumentos = listaInstrumentos;}
    public void setListaUnidades(List<UnidadMedida> listaUnidades) {this.listaUnidades = listaUnidades;}

    public void cargarDatos(JTable tbl, List<TipoInstrumentoObj>list,JComboBox comb) throws Exception {

        System.out.println("Cargando datos de lista\n");
        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
        comb.removeAllItems();
        for (TipoInstrumentoObj obj:list) {
            System.out.println("Recupere este tipo de instrumento " + obj.getNombre());
            obj.setUnidad(getUnidadNom(obj.getUnidadId()));
            Object[] newRow = {obj.getCodigo(),obj.getNombre(),obj.getUnidad()};

            modelo.addRow(newRow);
            //ServiceProxy.instance().findById(obj.getUnidadId());
            comb.addItem(obj.getCodigo());

        }
       // setListaInstrumentos(list);
    }
    public void cargarDatosUnidad(List<UnidadMedida>list, JComboBox combU) throws Exception {

        System.out.println("Cargando datos de lista unidad\n");
        combU.removeAllItems();
        for (UnidadMedida obj:list) {
            System.out.println("Recupere esta unidad: " + obj.getNombre());
            combU.addItem(obj.getNombre());
            //ServiceProxy.instance().findById(obj.getUnidadId());
            //comb.addItem(ServiceProxy.instance().getPrueba());
        }
        // setListaInstrumentos(list);
    }

    public void updateLista() throws Exception {
        //ServiceProxy.instance().inicializar_cliente();
        ServiceProxy.instance().readUnidadesMedida(listaUnidades);
        ServiceProxy.instance().read(listaInstrumentos);

    }

    //busqueda--------------------------------------
    public boolean busquedaPorNombre(String buscar, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        //return dom.buscarTiposInsPorNom(nom, tbl);
        boolean alguno = false;
        String buscarLower = buscar.toLowerCase();
        for(TipoInstrumentoObj obj: listaInstrumentos){
            String nombreActLower= obj.getNombre().toLowerCase();
            String unidadActLower= obj.getUnidad().toLowerCase();
            if (nombreActLower.contains(buscarLower)|unidadActLower.contains(buscarLower)){
                Object[] newRow = {obj.getCodigo(), obj.getNombre(), obj.getUnidad()};
                DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
                if (!alguno) {
                    modelo.setRowCount(0);
                }
                modelo.addRow(newRow);
                tbl.setRowSelectionInterval(0, 0);
                MouseEvent clickEvent = new MouseEvent(tbl, MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(),
                        0, 0, 0, 1, false);
                tbl.dispatchEvent(clickEvent); //activa el listener como si fuera un click
                alguno = true;
            }
        }
        return alguno;
    }

    public boolean busquedaPorCodigo(String cod, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        //return dom.buscarTiposInsPorCod(cod, tbl);
        boolean alguno = false;
        for(TipoInstrumentoObj obj: listaInstrumentos) {
            String codigoActual = obj.getCodigo().toLowerCase();
            if (codigoActual.contains(cod.toLowerCase())) {

                Object[] newRow = {cod, obj.getCodigo(), obj.getUnidad()};
                DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
                modelo.setRowCount(0);
                modelo.addRow(newRow);
                tbl.setRowSelectionInterval(0, 0);
                MouseEvent clickEvent = new MouseEvent(tbl, MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(),
                        0, 0, 0, 1, false);
                tbl.dispatchEvent(clickEvent); //activa el listener como si fuera un click
                alguno = true;
            }
        }
        return alguno;
    }


    //Reporte------------------------------------------
    public void generarReporteTipos(){

    }
    public void generarReporteGeneral(){
        reporte.createPDF();
    }


    public int getUnidadID(String selectedItem) {
        for(UnidadMedida u: listaUnidades){
            if(u.getNombre().equals(selectedItem)){
                return u.getIdUnidadMedida();
            }
        }
        return 0;
    }
    public String getUnidadNom(int selectedItem) {
        for(UnidadMedida u: listaUnidades){
            if(u.getIdUnidadMedida()==(selectedItem)){
                return u.getNombre();
            }
        }
        return "-";
    }
}
