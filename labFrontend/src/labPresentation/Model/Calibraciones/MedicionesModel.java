package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;
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
    public void cargar_tablaMediciones(Calibraciones calibracion, Instrumento obj, int med) {
        DefaultTableModel model = (DefaultTableModel) tablaM.getModel();

        System.out.println(obj.toString() + "    " + med);
        System.out.println("Cont " + model.getRowCount());

        if (model.getRowCount() > 0) {
            this.limpiar_tabla(model);
        }

        if (calibracion != null && !calibracion.getMedicionesL().isEmpty()) {
            this.limpiar_tabla(model);
        }

        List<Mediciones> lis = new ArrayList<>();
        double valorIntervalo = (double) (obj.getMaximo() - obj.getMinimo()) / med;
        double valorM = obj.getMinimo();
        for (int i = 1; i <= med; i++) {
            Mediciones medicion = new Mediciones(i, valorM);
            lis.add(medicion);
            Object[] fila = new Object[]{i, valorM, null};
            model.addRow(fila);
            Object value = model.getValueAt(i - 1, 2);
            if (value != null) {
                medicion.setValorMarcado(((Double) value).doubleValue());
            }
            valorM += valorIntervalo;
        }
        if (calibracion != null) {
            calibracion.setMedicionesL(lis);
        }
    }


        //this.validarToleranciaMedicion(listM, obj);
    public List<Mediciones>  obtenerLisMediciones (Instrumento instrumentoCalibrado, int cantidadDeMediciones) {
        List<Mediciones> lis = new ArrayList<>();
        double valorIntervalo = (double) (instrumentoCalibrado.getMaximo() - instrumentoCalibrado.getMinimo()) / cantidadDeMediciones;
        double valorM = instrumentoCalibrado.getMinimo();
        for (int i = 1; i <= cantidadDeMediciones; i++) {
            lis.add(new Mediciones(i,valorM));
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

    public List<Mediciones> getListM() {
        return listM;
    }
}
