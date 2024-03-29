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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TiposInstrumentosModel {
    private List<TipoInstrumentoObj> listaInstrumentos;
    private List<UnidadMedida> listaUnidades;

    private JTable tbl_tiposInst;
    private JComboBox cmB_tiposIns;
    private JComboBox cmB_UnidadMed;
    private PDF reporte;

    //Metodos----------------------------------------------------------------------------------------------------------
    public TiposInstrumentosModel(JTable table, JComboBox comb, JComboBox combUnidad, PDF pdf) throws ParserConfigurationException, IOException, TransformerException {
        listaInstrumentos = new ArrayList<>();
        cmB_tiposIns = comb;
        cmB_UnidadMed = combUnidad;
        tbl_tiposInst = table;
        reporte = pdf;
    }
    public void save(TipoInstrumentoObj ins) throws Exception {

        ins.setUnidad(getUnidadNom(ins.getUnidadId()));
        try{
            ServiceProxy.instance().create(ins);
            updateLista();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void eliminar(String ins, int fila){
        try{
            ServiceProxy.instance().delete(ins);
            DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
            listaInstrumentos.remove(ins);//elimina elemento de la lista
            modelo.removeRow(fila);         //elimina elemento de la tabla
            updateLista();


        } catch (Exception e){

        }

    }

    public boolean update (TipoInstrumentoObj ins) throws Exception {
        boolean re;
                ServiceProxy.instance().update(ins);
                updateLista();
        re = true;

        return re;
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
            comb.addItem(obj.getCodigo());

        }
       reporte.setListaTipos(list);
    }
    public void cargarDatosUnidad(List<UnidadMedida>list, JComboBox combU) throws Exception {

        System.out.println("Cargando datos de lista unidad\n");
        combU.removeAllItems();
        for (UnidadMedida obj:list) {
            System.out.println("Recupere esta unidad: " + obj.getNombre());
            combU.addItem(obj.getNombre());

        }

    }

    public void updateLista() throws Exception {

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
    public void generarReporteGeneral() throws FileNotFoundException {
        reporte.createPDF("tipos de instrumentos");
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
