package labPresentation.Model.Calibraciones;

import Protocol.Instrumento;
import Protocol.Mediciones;

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
    public void cargar_tablaMediciones(Instrumento obj, int med) {
        DefaultTableModel model = (DefaultTableModel) tablaM.getModel();

        System.out.println(obj.toString() + "    " + med);
        System.out.println("Cont " + model.getRowCount());

        if (model.getRowCount() > 0) {
            this.limpiar_tabla(model);
        }

        if (!listM.isEmpty()) {
            this.limpiar_tabla(model);
        }

        List<Mediciones> lis = new ArrayList<>();
        double valorIntervalo = (double) (obj.getMaximo() - obj.getMinimo()) / med;
        double valorM = obj.getMinimo();
        for (int i = 1; i <= med; i++) {
            lis.add(new Mediciones(i, valorM, 0.0));
            Object[] fila = new Object[]{i, valorM, 0.0};
            model.addRow(fila);
            valorM += valorIntervalo;
        }
        //this.validarToleranciaMedicion(listM, obj);
    }


    public List<Mediciones>  obtenerLisMediciones (Instrumento instrumentoCalibrado, int cantidadDeMediciones) {
        List<Mediciones> lis = new ArrayList<>();
        double valorIntervalo = (double) (instrumentoCalibrado.getMaximo() - instrumentoCalibrado.getMinimo()) / cantidadDeMediciones;
        double valorM = instrumentoCalibrado.getMinimo();
        for (int i = 1; i <= cantidadDeMediciones; i++) {
            lis.add(new Mediciones(i,valorM, 0.0));
            valorM+=valorIntervalo;
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
