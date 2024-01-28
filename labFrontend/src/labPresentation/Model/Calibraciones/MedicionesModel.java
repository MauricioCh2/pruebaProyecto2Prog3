package labPresentation.Model.Calibraciones;

import Protocol.Instrumento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class MedicionesModel {
    private List<Mediciones> listM;
    JTable tablaM;

    public MedicionesModel(JTable tb) {
        this.tablaM = tb;
        listM = new ArrayList<>();
    }
    public MedicionesModel(){

    }
    public void cargar_tablaMediciones(Instrumento obj, int med){


        DefaultTableModel model = (DefaultTableModel) tablaM.getModel();

        System.out.println(obj.toString() +"    "+ med);
        System.out.println("Cont " + model.getRowCount());
        if (model.getRowCount()>0){this.limpiar_tabla(model);}
        if (!listM.isEmpty()) {this.reiniciar_lista();}
        this.obtenerMediciones(obj, med);
        for (int i = 0; listM.size() > i; i++){
            Object[] fila = new Object[]{i, listM.get(i).getValorReferencia(), "0"};
            model.addRow(fila);
            validarToleranciaMedicion(listM.get(i), obj);
        }

    }
    public void validarToleranciaMedicion(Mediciones mediciones, Instrumento instrumentoCalibrado) {
        try {
            double valorReferencia = mediciones.getValorReferencia();
            double valorLectura = mediciones.getValorMarcado();
            double tolerancia = instrumentoCalibrado.getTolerancia();
            double limiteInferior = valorReferencia - tolerancia;
            double limiteSuperior = valorReferencia + tolerancia;

            if (valorLectura < limiteInferior || valorLectura > limiteSuperior) {
                // mediciones.resaltar();
                JOptionPane.showMessageDialog(null, "El instrumento presenta un problema. La medición está fuera del rango de tolerancia.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void obtenerMediciones (Instrumento instrumentoCalibrado, int cantidadDeMediciones) {
        //Rangos de mediciones
        double valorMaximo = instrumentoCalibrado.getMaximo();  //Valor máximo.
        double valorMinimo = instrumentoCalibrado.getMinimo();  //Valor mínimo.
        double valorIntervalo = (valorMaximo - valorMinimo) / cantidadDeMediciones;

        for (int i = 0; i < cantidadDeMediciones; i++) {
            double valorReferencia = valorMinimo + i * valorIntervalo;
            listM.add(new Mediciones(valorReferencia, 0));
        }
    }
    public List<Mediciones>  obtenerLisMediciones (Instrumento instrumentoCalibrado, int cantidadDeMediciones) {
        List<Mediciones> lis = new ArrayList<>();
        //Rangos de mediciones
        double valorMaximo = instrumentoCalibrado.getMaximo();  //Valor máximo.
        double valorMinimo = instrumentoCalibrado.getMinimo();  //Valor mínimo.
        double valorIntervalo = (valorMaximo - valorMinimo) / cantidadDeMediciones;

        for (int i = 0; i < cantidadDeMediciones; i++) {
            double valorReferencia = valorMinimo + i * valorIntervalo;
            lis.add(new Mediciones(valorReferencia, 0));
        }
        return  lis;
    }

    public void limpiar_tabla(DefaultTableModel model){
        int filas = model.getRowCount();

        for (int a = 0; filas > a; a++) {
            model.removeRow(0);
        }
    }

    public void reiniciar_lista(){
        listM.clear();
    }

}
