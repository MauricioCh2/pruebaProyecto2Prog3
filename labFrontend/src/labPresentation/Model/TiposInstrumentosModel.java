package labPresentation.Model;

import Protocol.Listas.UnidadMedList;
import Protocol.TipoInstrumentoObj;
import Protocol.UnidadMedida;
import labLogic.ServiceProxy;
import labServer.dao.DAOUnidadMedida;
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
    private JComboBox cmBUnidadesMedidas;
    private DOM_tiposInstrumento dom;
    private PDF reporte;
    DAOUnidadMedida daoMedidas;

    //Metodos----------------------------------------------------------------------------------------------------------
    public TiposInstrumentosModel(JTable table, JComboBox comb) throws ParserConfigurationException, IOException, TransformerException {
        listaInstrumentos = new ArrayList<>();
        listaUnidades = new ArrayList<>();

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
        //    listaUnidades.add(unM);
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
            //dom.eliminarTipo(ins);
            //dom.cargaTiposATable(tbl_tiposInst, cmB_tiposIns);
            ServiceProxy.instance().delete(ins);


        } catch (Exception e){
//
        }

    }
    public List<UnidadMedida> getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(List<UnidadMedida> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }
    public boolean update (TipoInstrumentoObj ins) throws Exception {
        boolean re;
//        current.setCodigo(ins.getCodigo());
//        current.setNombre(ins.getNombre());
//        current.setUnidad(ins.getUnidad());
        //actualizar con lista
//        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
//        Object[] fila = new Object[]{current.getCodigo(), current.getNombre(), current.getUnidad()};
//        modelo.insertRow(tbl_tiposInst.getSelectedRow()+1, fila);
//        modelo.removeRow(tbl_tiposInst.getSelectedRow());
        //re = dom.updateTipoInstrumento(ins);
                ServiceProxy.instance().update(ins);
        re = true;

       // dom.cargaTiposATable(tbl_tiposInst, cmB_tiposIns);
        ;
        return re;
    }
    private boolean createTipoXML(TipoInstrumentoObj tIns){
        return dom.addTipoInstrumento(tIns);
    }

    public List<TipoInstrumentoObj> getListaInstrumentos() {
        return listaInstrumentos;
    }

    public void cargarDatos(JTable tbl) throws Exception {
        //dom.cargaTiposATable(tbl, comb);
        ServiceProxy.instance().read(listaInstrumentos);
        ServiceProxy.instance().readUnidadesMedida(new UnidadMedList());

        //System.out.println("Si estoy devolviendo la lista  mi valor es: "+UnidadMedList.getList().get(0).getNombre());

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
