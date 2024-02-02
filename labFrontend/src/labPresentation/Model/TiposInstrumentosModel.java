package labPresentation.Model;

import Protocol.Listas.UnidadMedList;
import Protocol.TipoInstrumentoObj;
import Protocol.UnidadMedida;
import labLogic.ServiceProxy;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TiposInstrumentosModel {
    private List<TipoInstrumentoObj> listaInstrumentos;
    private List<UnidadMedida> listaUnidades;
    private TipoInstrumentoObj current;
    private JTable tbl_tiposInst;
    private JComboBox cmB_tiposIns;
    private DOM_tiposInstrumento dom;
    private PDF reporte;

    //Metodos----------------------------------------------------------------------------------------------------------
    public TiposInstrumentosModel(JTable table, JComboBox comb) throws ParserConfigurationException, IOException, TransformerException {
        listaInstrumentos = new ArrayList<>();
        cmB_tiposIns = comb;
        tbl_tiposInst = table;
        dom = new DOM_tiposInstrumento();
        reporte = new PDF("tipos de instrumentos", dom);
    }
    public boolean save(TipoInstrumentoObj ins) throws Exception {
        DefaultTableModel model = (DefaultTableModel) tbl_tiposInst.getModel();
        boolean guardado = true;
                ServiceProxy.instance().create(ins);
                ServiceProxy.instance().send_tipos_instrumento(ins);
                Object[] newRow = {ins.getCodigo(),ins.getNombre(),ins.getUnidadId()};
                model.addRow(newRow);
        return guardado;
    }


    public void eliminar(String ins, int fila){
        try{
            DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
            listaInstrumentos.remove(ins);//elimina elemento de la lista
            modelo.removeRow(fila);         //elimina elemento de la tabla
            //dom.eliminarTipo(ins);
            //dom.cargaTiposATable(tbl_tiposInst, cmB_tiposIns);
            ServiceProxy.instance().delete(ins);


        } catch (Exception e){
//
        }

    }

    public boolean update (TipoInstrumentoObj ins) throws Exception {
        boolean re;

                ServiceProxy.instance().update(ins);
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

    public void cargarDatos(JTable tbl, List<TipoInstrumentoObj>list,JComboBox comb) throws Exception {
        System.out.println("Cargando datos de lista\n");
        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
        for (TipoInstrumentoObj obj:list) {
            System.out.println("Recupere este tipo de instrumento " + obj.getNombre());

            Object[] newRow = {obj.getCodigo(),obj.getNombre(),obj.getUnidadId()};

            modelo.addRow(newRow);
        }
       // setListaInstrumentos(list);
    }

    public void inicializar_lista(){
        ServiceProxy.instance().inicializar_cliente();
    }

    //busqueda--------------------------------------
    public boolean busquedaPorNombre(String nom, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        return dom.buscarTiposInsPorNom(nom, tbl);
    }

    public boolean busquedaPorCodigo(String cod, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        return dom.buscarTiposInsPorCod(cod, tbl);
    }


    //Reporte------------------------------------------
    public void generarReporteTipos(){

    }
    public void generarReporteGeneral(){
        reporte.createPDF();
    }



}
