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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CalibracionesModel extends Observable {
    private InstrumentosModel InsActual;
    private Calibraciones caliActual;
    private List<Calibraciones> listC;
    private List<Mediciones> listM;
    private Mediciones medicionActual;
    JTable tablaC;

    DOM_calibraciones dom;
    private PDF reporte;
    private int modo;
    private int cambiarEstadoPropiedades = NINGUNA;
    private static int NINGUNA = 0;
    private static int LISTA = 1;
    private static int ACTUAL = 2;

    public  CalibracionesModel(JTable tabC) throws ParserConfigurationException, IOException, TransformerException {
        dom = new DOM_calibraciones();
        tablaC = tabC;
        reporte = new PDF("calibraciones", dom);
    }
    public void save(Calibraciones calA) throws Exception {
        boolean guardado = false;
        guardado = ServiceProxy.instance().create(calA);
        if (guardado) {
            DefaultTableModel modelo = (DefaultTableModel) tablaC.getModel();
            Object[] fila = new Object[]{calA.getNumeroCalibracion(), calA.getFecha(), calA.getNumeroMediciones()};
            modelo.addRow(fila);
        }

    }

    public void cargarDatosALista(Instrumento ins, JTable tbl){
        dom.cargarCalibracionesAtList(ins, tbl);
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
    public void commit() {
        setChanged();
        notifyObservers(cambiarEstadoPropiedades);
        cambiarEstadoPropiedades = NINGUNA;
    }

    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }
    public void iniciar(List<Calibraciones> listaCali,JTable table) throws ParserConfigurationException, IOException, TransformerException {
        setCalibracionActual(new Calibraciones(Integer.valueOf(caliActual.getNumeroCalibracion()), InsActual, caliActual.getFecha(), Integer.valueOf(String.valueOf(caliActual.getNumeroMediciones())), tablaC));
        setListaCalibraciones(listaCali);
        setInstrumentoActual(new InstrumentosModel(table));
        setListaMediciones(new ArrayList<>());
        setMedicionActual(new Mediciones());
    }
    public List<Calibraciones> getListaCalibraciones() {
        return listC;
    }

    public void setListaCalibraciones(List<Calibraciones> listaCalibraciones) {
        List<Mediciones> med = new ArrayList<>();
        for (int i = 0; i < listM.size(); i++) {
            med.add(new Mediciones(listM.get(i)));
        }
        this.listM = med;
        cambiarEstadoPropiedades += LISTA;
    }

    public Calibraciones getCalibracionActual() {
        return caliActual;
    }

    public void setCalibracionActual(Calibraciones calibracionActual) {
        this.caliActual = calibracionActual;
        cambiarEstadoPropiedades += ACTUAL;
    }

    public InstrumentosModel getInstrumentoActual() {
        return InsActual;
    }

    public void setInstrumentoActual(InstrumentosModel instrumentoActual) {
        this.InsActual = instrumentoActual;
        cambiarEstadoPropiedades += ACTUAL;
    }

    public List<Mediciones> getListaMediciones() {
        return listM;
    }

    public void setListaMediciones(List<Mediciones> listaMediciones) {
        this.listM = listaMediciones;
        cambiarEstadoPropiedades += LISTA;
    }

    public Mediciones getMedicionActual() {
        return medicionActual;
    }

    public void setMedicionActual(Mediciones medicionActual) {
        this.medicionActual = medicionActual;
        cambiarEstadoPropiedades += ACTUAL;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
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
