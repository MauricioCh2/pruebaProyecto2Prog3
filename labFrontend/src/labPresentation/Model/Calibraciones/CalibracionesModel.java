package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;
import Protocol.Instrumento;
import Protocol.Mediciones;
import labLogic.ServiceProxy;
import labPresentation.Model.InstrumentosModel;
import labPresentation.Model.PDF;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

public class CalibracionesModel {
    private InstrumentosModel InsActual;
    private Calibraciones caliActual;
    private List<Calibraciones> listC;
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
            try{
                ServiceProxy.instance().create(calA);
                DefaultTableModel modelo = (DefaultTableModel) tablaC.getModel();
                Object[] fila = new Object[]{calA.getNumeroCalibracion(), calA.getFecha(), calA.getNumeroMediciones()};
                modelo.addRow(fila);
            } catch (Exception e) {
                System.out.println("Error al guardar calibracion: "+ e.getMessage());
            }

    }
    public boolean calibrado(Mediciones mediciones, Instrumento instrumentoCalibrado) {
        String mensaje;
        try {
            double valorReferencia = mediciones.getValorReferencia();
            double valorLectura = mediciones.getValorMarcado();
            double tolerancia = instrumentoCalibrado.getTolerancia();
            double limiteInferior = valorReferencia - tolerancia;
            double limiteSuperior = valorReferencia + tolerancia;

            if (valorLectura < limiteInferior || valorLectura > limiteSuperior) {
                mensaje =  "<html><div style='text-align: center;'> Según la última calibración, registrada, <br> el instrumento " + instrumentoCalibrado.toString() + " <br>se encuentra fuera del rango de tolerancia <br>, y requiere ser calibrado.</div></html>";
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        mensaje = "El instrumento: "+ instrumentoCalibrado.toString() +" se encuentra calibrado.";
        return true;
    }
    public void cargarDatos( JTable tbl, List<Calibraciones> list){
        System.out.println("Cargando datos de lista\n");
        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
        for (Calibraciones obj:list) {
            System.out.println("Recupere esta calibraion " + obj.getNumeroCalibracion());

            Object[] newRow = {obj.getNumeroCalibracion(),obj.getStringFecha(),obj.getNumeroMediciones()};

            modelo.addRow(newRow);
            //ServiceProxy.instance().findById(obj.getUnidadId());
            //comb.addItem(obj.getCodigo());

        }
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
    /*    public boolean busquedaPorSerie(String cod, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        //return dom.buscarInstrumentosPorSerie(cod, tbl);
        boolean alguno = false;
        for(Instrumento obj: listaInstrumento) {
            String codigoActual = obj.getSerie().toLowerCase();
            if (codigoActual.contains(cod.toLowerCase())) {

                Object[] newRow = {obj.getSerie(),obj.getDescripcion(),obj.getMinimo(), obj.getMaximo(), obj.getTolerancia()};
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
    }*/
    public boolean busquedaCalibracion(int numC, JTable tbl)   {
        boolean encontrado = false;
        for (Calibraciones calibraciones : listC) {
            int codigoActual = calibraciones.getNumeroCalibracion();
            if (codigoActual == numC) {
                Object[] newRow = {calibraciones.getNumeroCalibracion(), calibraciones.getFecha(), calibraciones.getNumeroMediciones()};
                DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
                modelo.setRowCount(0);
                modelo.addRow(newRow);
                tbl.setRowSelectionInterval(0, 0);
                MouseEvent clickEvent = new MouseEvent(tbl, MouseEvent.MOUSE_CLICKED,
                        System.currentTimeMillis(),
                        0, 0, 0, 1, false);
                tbl.dispatchEvent(clickEvent); //activa el listener como si fuera un click
                encontrado = true;
                break;
            }
        }
        return encontrado;
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
    public void setListC(List<Calibraciones> listC) {
        this.listC = listC;
    }

}
