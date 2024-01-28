package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;
import labPresentation.Model.InstrumentosModel;
import labPresentation.Model.PDF;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;

public class CalibracionesModel {
    private InstrumentosModel InsActual;
    private Calibraciones caliActual;
    private ListaCalibraciones listC;
    private List<Mediciones> listM;
    JTable tablaC;

    DOM_calibraciones dom;
    private PDF reporte;

    public  CalibracionesModel(JTable tabC) throws ParserConfigurationException, IOException, TransformerException {
        dom = new DOM_calibraciones();
        tablaC = tabC;
        reporte = new PDF("calibraciones", dom);
    }
    public void save(Calibraciones calA) {
        boolean guardado = false;
        guardado = addCalibracionXML(calA);
        if (guardado) {
            DefaultTableModel modelo = (DefaultTableModel) tablaC.getModel();
            Object[] fila = new Object[]{calA.getNumeroCalibracion(), calA.getFecha(), calA.getNumeroMediciones()};
            modelo.addRow(fila);
        }

    }
    public void cargarDatos(JTable tbl, String noSer){
        dom.cargarCalibraciones(tbl, noSer);
        //ins.agregar_categoriaCB(instrumento);
    }
    boolean addCalibracionXML(Calibraciones cal){
        return dom.addCalibraciones(cal);
    }
    public void eliminar(String no_ser, String cal, int fila){
        try{
            DefaultTableModel modelo = (DefaultTableModel) tablaC.getModel();
            modelo.removeRow(fila);         //elimina elemento de la tabla
            dom.eliminarCalibraciones(no_ser,cal);
            dom.cargarCalibraciones(tablaC, cal);

        } catch (Exception e){
//
        }
    }
    public boolean busquedaCalibracion(String noSerie,String numCal, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        return dom.buscarCalibracionPorNum(noSerie, numCal ,tbl);//este es con numero de serie del instrumento
    }

    private void actualizar(Calibraciones actual) {
        //caliActual.setNumero(actual.getNumero());
        caliActual.setFecha(actual.getFecha());
        caliActual.setMedicionesL(actual.getMedicionesL());
       // JTable tabCalibraciones = new JTable(tablacalibraciones);
        //int rowIndex = tabCalibraciones.getSelectedRow();
        //tablacalibraciones.setValueAt(caliActual.getNumero(), rowIndex, TableCalibraciones.NumeroCalibracion);

    }

    public void generarReporte(String stri,String ser){
        reporte.createPDFreportes(stri,ser);
    }
    public void generarReporteGen(String stri){

        reporte.createPDFreportesGeneral(stri);
    }


}
