package labPresentation.Model;

import Protocol.Instrumento;
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

public class InstrumentosModel {

        private ListaInstrumentos_E listaInstrumentos_e = new ListaInstrumentos_E();
        private List<Instrumento> listaInstrumento;
        Instrumento current;
        JTable tbl_tiposInst;
        private DOM_Instrumento dom;
        private PDF reporte;
        public InstrumentosModel(JTable table) throws ParserConfigurationException, IOException, TransformerException {
            tbl_tiposInst = table;
            dom = new DOM_Instrumento();
            reporte = new PDF("instrumentos", dom);
            listaInstrumento = new ArrayList<>();
        }

    public List<Instrumento> getListaInstrumento() {
        return listaInstrumento;
    }

    public void setListaInstrumento(List<Instrumento> listaInstrumento) {
        this.listaInstrumento = listaInstrumento;
    }

    public boolean instrumento_existente(String serie){
            Instrumento ins = seleccionar_instrumento_Serie(serie);
            return ins != null;
        }

        public Instrumento seleccionar_instrumento_Serie(String serie){
            return listaInstrumentos_e.obtener(serie);
        }

        public void save(Instrumento ins) throws Exception {
            current = seleccionar_instrumento_Serie(ins.getSerie());
            boolean guardado = false;
            if (current == null) {
                listaInstrumentos_e.agregar(ins);
                ServiceProxy.instance().create(ins);
                //guardado = createTipoXML(ins);
                //if (guardado) {
                DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
                Object[] fila = new Object[]{ins.getSerie(), ins.getDescripcion(), ins.getMinimo(), ins.getMaximo(), ins.getTolerancia()};
                modelo.addRow(fila);
                //}
            } //else this.actualizar(ins);
        }

    public boolean actualizar(Instrumento ins) throws Exception {
        boolean respuesta;
        //current = seleccionar_instrumento_Serie(ins.getSerie());
        //current.setDescripcion(ins.getDescripcion());
        //current.setMaximo(ins.getMaximo());
        //current.setMinimo(ins.getMinimo());
        //current.setTolerancia(ins.getTolerancia());
        //current.setTipo(ins.getTipo());
        //respuesta = dom.updateInstrumento(ins);
        //dom.cargaInstrumentosATable(tbl_tiposInst);//refresca la tabla
//        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
//        Object[] fila = new Object[]{current.getSerie(), current.getDescripcion(), current.getMinimo(), current.getMaximo(), current.getTolerancia()};
//        modelo.insertRow(tbl_tiposInst.getSelectedRow()+1, fila);
//        modelo.removeRow(tbl_tiposInst.getSelectedRow());
        respuesta = true;
                ServiceProxy.instance().update(ins);
        return respuesta;
    }

    public void eliminar_elemento(String serie) throws Exception {
        Instrumento object = seleccionar_instrumento_Serie(serie);
        listaInstrumentos_e.getList().remove(object);
        DefaultTableModel modelo = (DefaultTableModel) tbl_tiposInst.getModel();
        modelo.removeRow(tbl_tiposInst.getSelectedRow());
        //dom.eliminarInstrumeto(serie);
        //dom.cargaInstrumentosATable(tbl_tiposInst);
        ServiceProxy.instance().deleteInstrumentoId(serie);
    }
    public void cargarDatos(JTable tbl, List<Instrumento> list ) throws Exception {
        System.out.println("Cargando datos de lista\n");
        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
        for (Instrumento obj:list) {
            System.out.println("Recupere este  instrumento " + obj.getSerie());
            Object[] newRow = {obj.getSerie(),obj.getDescripcion(),obj.getMinimo(), obj.getMaximo(), obj.getTolerancia()};
            modelo.addRow(newRow);

        }
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

    public void updateLista() throws Exception {
        ServiceProxy.instance().read_instrumentos();
    }
}

//}




