package labPresentation.Model;

import Protocol.TipoInstrumentoObj;
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
        //current = listaInstrumentos.get(ins.getCodigo());//busca el elemento
        boolean guardado = true;
       // if (current == null) { // en caso de no existir agregara uno nuevo
            listaInstrumentos.add(ins);
            //guardado = createTipoXML(ins);
            //if(guardado){
              ServiceProxy.instance().create(ins);

                Object[] newRow = {ins.getCodigo(), ins.getNombre(), ins.getUnidad()};
                DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
                modelo.addRow(newRow);
                //return  guardado;
           // }
       // }else{// en caso de no actualizara uno
            //this.update(ins);
        //}
        return guardado;
    }
    public void eliminar(String ins, int fila){
        try{
            DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
            listaInstrumentos.remove(ins);//elimina elemento de la lista
            modelo.removeRow(fila);         //elimina elemento de la tabla
            dom.eliminarTipo(ins);
            dom.cargaTiposATable(tbl_tiposInst, cmB_tiposIns);

        } catch (Exception e){
//
        }

    }

    public boolean update (TipoInstrumentoObj ins) {
        boolean re;
//        current.setCodigo(ins.getCodigo());
//        current.setNombre(ins.getNombre());
//        current.setUnidad(ins.getUnidad());
        //actualizar con lista
//        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
//        Object[] fila = new Object[]{current.getCodigo(), current.getNombre(), current.getUnidad()};
//        modelo.insertRow(tbl_tiposInst.getSelectedRow()+1, fila);
//        modelo.removeRow(tbl_tiposInst.getSelectedRow());
        re = dom.updateTipoInstrumento(ins);
        dom.cargaTiposATable(tbl_tiposInst, cmB_tiposIns);
        ;
        return re;
    }
    private boolean createTipoXML(TipoInstrumentoObj tIns){
        return dom.addTipoInstrumento(tIns);
    }

    public List<TipoInstrumentoObj> getListaInstrumentos() {
        return listaInstrumentos;
    }

    public void cargarDatos(JTable tbl, JComboBox comb){
        dom.cargaTiposATable(tbl, comb);
        //ins.agregar_categoriaCB(instrumento);
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
