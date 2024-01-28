package labPresentation.Controller;

import Protocol.Calibraciones;
import Protocol.Instrumento;
import labPresentation.Model.Calibraciones.CalibracionesModel;
import labPresentation.Model.Calibraciones.MedicionesModel;
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

public class CalibracionesController {
    private static CalibracionesView calibracionesView;

    //private Calibraciones Model;
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

    public static void setInstru(Instrumento instru) {
        CalibracionesController.instru = instru;
        modelo.cargarDatos(tableCalibraciones, instru.getSerie());

    }

    public static void cargarEstado() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {

       // modelo.cargarDatos(tableCalibraciones, modelo.busquedaActualizado(instru).getSerie());
    }

    public CalibracionesController(){

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

    public static void guardarCalibraciones(){
        int num = 0;
        if (!EDITAR_MEDICIONES) {
            num = 0;
            try {
                if (textMediciones.getText().isEmpty()) {
                    throw new Exception("La cantidad de mediciones, no ha sido completada. Por favor, complete el espacio.");
                } else {
                    num = Integer.parseInt(textMediciones.getText());
                    System.out.println("Valor de mediciones que se estan agregando " + instru.getMinimo());

                    if (num < (instru.getMaximo() + 1)) {
                        if (num >= 2) {
                            int numM = Integer.parseInt(textMediciones.getText());
                            LocalDate date = LocalDate.now();
                            numeroCalibracion = calibracionesView.getTableCalibraciones().getModel().getRowCount(); // needed it
                            numeroCalibracion++;
                            System.out.println("Numero agregar: " + numeroCalibracion );
                            System.out.println("#calib" + numeroCalibracion);
                            Calibraciones calibraciones = new Calibraciones(numeroCalibracion, instru, date.toString(), numM);
                            modelo.save(calibraciones);
                            currentC = calibraciones;
                            limpiar();
                            calibracionesView.getBorrarButton().setEnabled(false);
                            JOptionPane.showMessageDialog(null, "Calibracion agregada");
                            cargarEstado();
                        } else {
                            throw new Exception("El valor de la cantidad de mediciones, no ha sido completada correctamente. Cantidad minima permitida 2.");
                        }
                    } else {
                        throw new Exception("El valor de la cantidad de mediciones, no ha sido completada correctamente.La cantidad máxima no debe superar el máximo del instrumento.");
                    }

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else limpiar();//actualizar mediciones
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
        modelo_mediciones.limpiar_tabla((DefaultTableModel) tableMediciones.getModel());
        if (EDITAR) {
            textNumero.setText(numeroActual);
        }
    }
    public static  void buscarCalibraciones(){
        try {
            if(textNumeroB.getText().isEmpty() ){
                throw new Exception("El campo de número de búsqueda de la calibración, se encuentra vacío. Por favor, complete el espacio.");
            }else{
                    if(!modelo.busquedaCalibracion(instru.getSerie(),textNumeroB.getText(),tableCalibraciones)){//este buscara por numero con un equal
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
    public static class BtnsCalibraciones implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Guardar": {
                    guardarCalibraciones();
                    break;
                }
                case "Borrar": {
                    borrar();
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

        private void borrar() {
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "¿Está seguro de querer borrar esta calibracion?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                int valFil = tableCalibraciones.getSelectedRow();//obtiene el valor de la fila
                Object objCod = tableCalibraciones.getValueAt(valFil, 0); //obtiene el codigo en forma de Object
                String cod = objCod.toString(); // lo convertimos a string
                modelo.eliminar(instru.getSerie(),cod, valFil); //elimina de la lista y de la tabla
                //reseteamos GUI
                calibracionesView.getBorrarButton().setEnabled(false);
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
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        private void cargar_tablaMediciones() {
            //System.out.println("Valor: " + Integer.valueOf((String) tableCalibraciones.getValueAt(tableCalibraciones.getSelectedRow(), 2)));
            int med = Integer.valueOf((String) tableCalibraciones.getValueAt(tableCalibraciones.getSelectedRow(), 2));

            modelo_mediciones.cargar_tablaMediciones(instru, med);
            EDITAR_MEDICIONES = true;// I needed it
            calibracionesView.getTextMediciones().setEnabled(false);// I needed it


        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}

    }

public static String toStringt(){
        if(instru != null){
            return  instru.getSerie() + " - " +  instru.getDescripcion() + "(" + instru.getMinimo()+"-"+instru.getMaximo() +" "+instru.getUnidad() +")";
        }else{
            return "No hay instrumento cargado";
        }

}

public static void update(){
        calibracionesView.getTx_instrumento().setText(toStringt());
        calibracionesView.getTx_instrumento().setForeground(Color.RED);
}


}