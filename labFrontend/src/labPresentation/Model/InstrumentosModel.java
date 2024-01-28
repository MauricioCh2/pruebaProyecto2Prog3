package labPresentation.Model;

import Protocol.Instrumento;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

public class InstrumentosModel {

        private ListaInstrumentos_E listaInstrumentos_e = new ListaInstrumentos_E();
        Instrumento current;
        JTable tbl_tiposInst;
        private DOM_Instrumento dom;
        private PDF reporte;
        public InstrumentosModel(JTable table) throws ParserConfigurationException, IOException, TransformerException {
            tbl_tiposInst = table;
            dom = new DOM_Instrumento();
            reporte = new PDF("instrumentos", dom);
        }
        public boolean instrumento_existente(String serie){
            Instrumento ins = seleccionar_instrumento_Serie(serie);
            return ins != null;
        }

        public Instrumento seleccionar_instrumento_Serie(String serie){
            return listaInstrumentos_e.obtener(serie);
        }

        public void save(Instrumento ins) {
            current = seleccionar_instrumento_Serie(ins.getSerie());
                boolean guardado = false;
                if (current == null) {
                    listaInstrumentos_e.agregar(ins);
                    guardado = createTipoXML(ins);
                    if (guardado) {
                        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
                        Object[] fila = new Object[]{ins.getSerie(), ins.getDescripcion(), ins.getMinimo(), ins.getMaximo(), ins.getTolerancia()};
                        modelo.addRow(fila);
                    }
                } //else this.actualizar(ins);
        }

    public boolean actualizar(Instrumento ins){
        boolean respuesta;
        //current = seleccionar_instrumento_Serie(ins.getSerie());
        //current.setDescripcion(ins.getDescripcion());
        //current.setMaximo(ins.getMaximo());
        //current.setMinimo(ins.getMinimo());
        //current.setTolerancia(ins.getTolerancia());
        //current.setTipo(ins.getTipo());
        respuesta = dom.updateInstrumento(ins);
        dom.cargaInstrumentosATable(tbl_tiposInst);//refresca la tabla
//        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
//        Object[] fila = new Object[]{current.getSerie(), current.getDescripcion(), current.getMinimo(), current.getMaximo(), current.getTolerancia()};
//        modelo.insertRow(tbl_tiposInst.getSelectedRow()+1, fila);
//        modelo.removeRow(tbl_tiposInst.getSelectedRow());
        return respuesta;
    }

    public void eliminar_elemento(String serie){
        Instrumento object = seleccionar_instrumento_Serie(serie);
        listaInstrumentos_e.getList().remove(object);
        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
        modelo.removeRow(tbl_tiposInst.getSelectedRow());
        dom.eliminarInstrumeto(serie);
        dom.cargaInstrumentosATable(tbl_tiposInst);
    }
    public void cargarDatos(JTable tbl){
        dom.cargaInstrumentosATable(tbl);
        //ins.agregar_categoriaCB(instrumento);
    }
    public ListaInstrumentos_E getListaInstrumentosE(){
        return listaInstrumentos_e;
    }
    private boolean createTipoXML(Instrumento ins){return dom.addInstrumento(ins);}

    //busqueda--------------------------------------
    public boolean busquedaPorDescripcion(String nom, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        return dom.buscarInstrumentosPorDescr(nom, tbl);
    }

    public boolean busquedaPorSerie(String cod, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        return dom.buscarInstrumentosPorSerie(cod, tbl);
    }

    public Instrumento busquedaInstrumento(String noSerie) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
            return dom.buscarInstrumentosPorSerie(noSerie);
    }
    public void generarReporte(){
        reporte.createPDF();
    }

}

//}




