package labPresentation.Controller;

import Protocol.Calibraciones;
import Protocol.IController;
import Protocol.Instrumento;
import Protocol.Protocol;
import labLogic.ServiceProxy;
import labPresentation.Model.Calibraciones.CalibracionesModel;
import labPresentation.Model.Calibraciones.MedicionesModel;
import labPresentation.Model.TableModel;
import labPresentation.View.CalibracionesView;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CalibracionesController implements IController {
    private static CalibracionesView calibracionesView;
    private static CalibracionesModel modelo;
    private static MedicionesModel modelo_mediciones;
    private static JTable tableCalibraciones;
    private static JTable tableMediciones;
    private static JTextField textNumero;
    private static JTextField textMediciones;
    private static JTextField textFecha;
    private static JTextField textNumeroB;
    private static boolean EDITAR;
    private static boolean EDITAR_MEDICIONES;
    private static  Instrumento instru =null;
    private static int numeroCalibracion;

    private static Calibraciones currentC;

    public static void setInstru(Instrumento instru) throws Exception {
        CalibracionesController.instru = instru;
        //modelo.cargarDatos(tableCalibraciones, instru.getSerie());
        updateLista(instru.getSerie());
    }
    public static Calibraciones getCurrentC() {
        return currentC;
    }

    public static void setCurrentC(Calibraciones currentC) {
        CalibracionesController.currentC = currentC;
    }

    public static void updateLista(String id) throws Exception {
        ServiceProxy.instance().readCalibracion(id);
    }

    public static void cargarEstado() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {

       // modelo.cargarDatos(tableCalibraciones, modelo.busquedaActualizado(instru).getSerie());
    }

    public CalibracionesController(){
        ServiceProxy.instance().setControllerCal(this);
    }
    public void init(CalibracionesView view) throws ParserConfigurationException, IOException, TransformerException {
        calibracionesView = view;
        tableCalibraciones = calibracionesView.getTableCalibraciones();
        tableMediciones = calibracionesView.getTableMediciones();
        textNumero = calibracionesView.getTextNumero();
        textMediciones = calibracionesView.getTextMediciones();
        textFecha = calibracionesView.getTextFecha();
        textNumeroB = calibracionesView.getTextNumeroB();
        modelo = new CalibracionesModel(calibracionesView.getTableCalibraciones());
        modelo_mediciones = new MedicionesModel(calibracionesView.getTableMediciones());
        EDITAR = false;
        //numeroCalibracion = calibracionesView.getTableCalibraciones().getModel().getRowCount();

    }

    public static void guardarCalibraciones() {
        int num = 0;
        if (!EDITAR_MEDICIONES) {
            num = 0;
            try {
                if (textMediciones.getText().isEmpty()) {
                    calibracionesView.getMedicionesLabel().setText("<html><u><font color='red'>Numero:</font></u></html>");
                    throw new Exception("La cantidad de mediciones, no ha sido completada. Por favor, complete el espacio.");
                } else {
                    num = Integer.parseInt(textMediciones.getText());
                    System.out.println("Valor de mediciones que se estan agregando " + instru.getMinimo());
                    int numComparacion = Math.abs((instru.getMaximo()-instru.getMinimo()))  + 1;
                    if (num <= numComparacion) {
                        if (num >= 2) {
                            int numM = Integer.parseInt(textMediciones.getText());
                            LocalDate date = LocalDate.now();
                            numeroCalibracion = calibracionesView.getTableCalibraciones().getModel().getRowCount(); // needed it
                            numeroCalibracion++;
                            System.out.println("Numero agregar: " + numeroCalibracion );
                            System.out.println("#calib" + numeroCalibracion);
                            Calibraciones calibraciones = new Calibraciones(numeroCalibracion, instru, date.toString(), numM);
                            if (currentC != null && currentC.getNo_SerieIns().equals(instru)) {
                                calibraciones.setMedicionesL(currentC.getMedicionesL());
                                //modelo.update(calibraciones);
                            } else {
                                modelo.save(calibraciones);
                            }
                            currentC = calibraciones;
                            setCurrentC(currentC);
                            limpiar();
                            calibracionesView.getBorrarButton().setEnabled(false);
                            JOptionPane.showMessageDialog(null, "Calibracion agregada");
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public static void limpiar(){
        textNumero.setEnabled(false);
        calibracionesView.getTextMediciones().setEnabled(true);
        String numeroActual = textNumero.getText();
        textNumero.setText("000");
        textMediciones.setText("");
        textFecha.setEnabled(false);
        textNumeroB.setText("");
        tableCalibraciones.clearSelection();
        EDITAR_MEDICIONES = false;
        modelo_mediciones.limpiar_tabla((DefaultTableModel) tableMediciones.getModel());
        if (EDITAR) {
            textNumero.setText(numeroActual);
        }
    }
    public static  void buscarCalibraciones(){
        try {
            if(textNumeroB.getText().isEmpty() ){
               calibracionesView.getNumeroLabelBusqueda().setText("<html><u><font color='red'>Numero:</font></u></html>");
                throw new Exception("El campo de número de búsqueda de la calibración, se encuentra vacío. Por favor, complete el espacio.");
            }else{
                    if(!modelo.busquedaCalibracion(Integer.parseInt(textNumeroB.getText()),tableCalibraciones)){//este buscara por numero con un equal
                        throw new Exception("No se encontraron resultados");
                    }else{
                        textNumeroB.setText("");
                        EDITAR= true;
                    }

            }
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void update(Object o, int pro) throws Exception {
        System.out.println("\n llegue al update ");
        if(pro == Protocol.RELOAD_CALIBRACION){
            modelo.setListC((java.util.List<Calibraciones>) o);
            modelo.cargarDatos(tableCalibraciones,(List<Calibraciones>) o);
        }
    }

    public static class BtnsCalibraciones implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Guardar": {
                    guardarCalibraciones();
                    break;
                }
                case "Borrar": {
                    try {
                        borrar();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
                case "Limpiar": {
                    limpiar();
                    break;
                }
                case "Buscar": {
                    buscarCalibraciones();
                    break;
                }
                case "Reporte": {
                    reporte();
                    break;
                }
                default:
            }
        }

        private void reporte() {
            Object[] opciones = {"Reporte", "Reporte General"};

            // Mostrar la ventana
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione el tipo de reporte", "Reportes", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, null);

            // Procesar la opción seleccionada
            if (opcion == 0) {
                // Generar el reporte
                modelo.generarReporte(toStringt(), instru.getSerie());
            } else {
                // Generar el reporte general
                modelo.generarReporteGen(toStringt());
            }
        }

        private void borrar() throws Exception {
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de borrar esta calibracion?. Puede tener mediciones asociadas.",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
               // limpiar();
                int valFil = tableCalibraciones.getSelectedRow();//obtiene el valor de la fila
                if (valFil >= 0) {
                    Object objCod = tableCalibraciones.getValueAt(valFil, 0); //obtiene el codigo en forma de Object
                    int cod = (int) objCod; // lo convertimos a string
                    modelo.eliminar(cod, valFil); //elimina de la lista y de la tabla
                    //reseteamos GUI
                    limpiar();
                    calibracionesView.getBorrarButton().setEnabled(false);
                }
            }
        }
    }

        public static class TablesCalibraciones implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
                rellenartextfields(e);
                calibracionesView.getBorrarButton().setEnabled(true);
                calibracionesView.getMedicionesPanel().setVisible(true);
                cargar_tablaMediciones();
            }


            private void rellenartextfields(MouseEvent e) {

                textNumero.setEnabled(false);
                try {
                    if (tableCalibraciones.getSelectedRow() != -1) {
                        if (e.getClickCount() == 1) {
                            DefaultTableModel modelo = (DefaultTableModel) tableCalibraciones.getModel();
                            textNumero.setText(String.valueOf(tableCalibraciones.getValueAt(tableCalibraciones.getSelectedRow(), 0)));
                            textFecha.setText(String.valueOf(tableCalibraciones.getValueAt(tableCalibraciones.getSelectedRow(), 1)));
                            textMediciones.setText(String.valueOf(tableCalibraciones.getValueAt(tableCalibraciones.getSelectedRow(), 2)));

                        }
                    } else {
                        throw new Exception("Seleccione una columna primero");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            public void cargar_tablaMediciones() {
                if (tableCalibraciones.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila en la tabla de calibraciones.");
                } else {
                    int med = (int) tableCalibraciones.getValueAt(tableCalibraciones.getSelectedRow(), 2);
                    modelo_mediciones.cargar_tablaMediciones(getCurrentC(),instru, med);
                    DefaultTableModel model = (DefaultTableModel) calibracionesView.getTableMediciones().getModel();
                    for (int i = 0; i < tableCalibraciones.getSelectedRow(); i++) {
                        model.isCellEditable(i, 2);
                        tableMediciones.getModel().isCellEditable(i,2);
                    }
                    calibracionesView.getTextMediciones().setEnabled(false);
                }
            }


            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        }

        public static String toStringt() {
            if (instru != null) {
                return instru.getSerie() + " - " + instru.getDescripcion() + "(" + instru.getMinimo() + " a " + instru.getMaximo() + " " + instru.getUnidad() + ")";
            } else {
                return "No hay instrumento cargado";
            }

        }

        public static void update() throws Exception {
            calibracionesView.getTx_instrumento().setText(toStringt());
            calibracionesView.getTx_instrumento().setForeground(Color.RED);
            updateLista(instru.getSerie());
        }


    }