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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static labPresentation.Controller.CalibracionesController.updateLista;

public class CalibracionesModel {
    private InstrumentosModel InsActual;
    private Calibraciones caliActual;
    private List<Calibraciones> listC;
    private List<Mediciones> listM;
    private JTable tablaC;
    private PDF reporte;

    public  CalibracionesModel(JTable tabC, PDF pdf) throws ParserConfigurationException, IOException, TransformerException {
        tablaC = tabC;
        reporte = pdf;
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
        try {
            if ( mediciones.toleranciaCorrecta((int) instrumentoCalibrado.getTolerancia()) ) {
                return true;
            }else {
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        return false;
    }
    public String getMensaje(Mediciones mediciones, Instrumento instrumentoCalibrado) {
        String mensaje = "";
        boolean calibrado = calibrado(mediciones, instrumentoCalibrado);
        if (calibrado) {
            mensaje = "El instrumento se encuentra calibrado.";
        } else {
            mensaje = "<html><div style='text-align: center;'> Según la última calibración registrada, <br> el instrumento " + InsActual.toString() + " <br>se encuentra fuera del rango de tolerancia <br>, y requiere ser calibrado.</div></html>";
            int i = 0;
            for (Mediciones medicion : listM) {
                if (!medicion.toleranciaCorrecta((int) instrumentoCalibrado.getTolerancia())) {
                    mensaje += "<br>La medición " + i + " está fuera del rango de tolerancia.";
                }
                i++;
            }
            JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        return mensaje;
    }


    public void cargarDatos( JTable tbl, List<Calibraciones> list, MedicionesModel medicionesModel){
        System.out.println("Cargando datos de lista\n");
        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        modelo.setRowCount(0);//nos aseguramos que la tabla esta vacia para no sobrecargarla
        for (Calibraciones obj:list) {
            System.out.println("Recupere esta calibraion " + obj.getNumeroCalibracion());

            Object[] newRow = {obj.getNumeroCalibracion(),obj.getStringFecha(),obj.getNumeroMediciones()};

            modelo.addRow(newRow);

        }
        reporte.setListaCalibraciones(list);
        reporte.setListaMediciones(medicionesModel.getListM());
    }

    public void eliminar(int id, int fila) throws Exception {
        for (int i = 0; i < listC.size(); i++) {
            Calibraciones calibracion = listC.get(i);
            if (calibracion.getNumeroCalibracion() == id) {
                listC.remove(i);
                DefaultTableModel modelo = (DefaultTableModel) tablaC.getModel();
                modelo.removeRow(fila);
                ServiceProxy.instance().delete(calibracion);
                break;
            }
        }
        updateLista(String.valueOf(id));
    }
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
    private void actualizar(Calibraciones actual) {
        //caliActual.setNumero(actual.getNumero());
        caliActual.setFecha(actual.getFecha());
        caliActual.setMedicionesL(actual.getMedicionesL());
       // JTable tabCalibraciones = new JTable(tablacalibraciones);
        //int rowIndex = tabCalibraciones.getSelectedRow();
        //tablacalibraciones.setValueAt(caliActual.getNumero(), rowIndex, TableCalibraciones.NumeroCalibracion);

    }

    public void generarReporte(String stri,String ser) throws FileNotFoundException {
        reporte.createPDFreportes(stri,ser,"calibraciones");

    }
    public void generarReporteGen() throws FileNotFoundException {
        reporte.createPDF("calibraciones");
    }
    public void setListC(List<Calibraciones> listC) {
        this.listC = listC;
    }

    public void edit(Calibraciones calibraciones) {
        try{
            ServiceProxy.instance().update(calibraciones);
            updateLista(calibraciones.getNo_SerieIns());
        } catch (Exception e) {
            System.out.println("Error al guardar calibracion: "+ e.getMessage());
        }
    }
    public boolean elementoExistente(String cod, JTable tbl) throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        boolean alguno = false;

        DefaultTableModel modelo = (DefaultTableModel) tbl.getModel();
        int rowCount = modelo.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String codigoActual = modelo.getValueAt(i, 0).toString().toLowerCase();

            if (codigoActual.equals(cod.toLowerCase())) {
                //tbl.setRowSelectionInterval(i, i);  // Seleccionar la fila correspondiente
                alguno = true;
                break;  // Salir del bucle tan pronto como se encuentra una coincidencia
            }
        }

        return alguno;
    }

}
