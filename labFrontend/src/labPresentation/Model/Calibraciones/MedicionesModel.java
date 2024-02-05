package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;
import Protocol.Instrumento;
import Protocol.Mediciones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
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

        List<Mediciones> lis = new ArrayList<>();
        double valorIntervalo = (double) (obj.getMaximo() - obj.getMinimo()) / med;
        double valorM = obj.getMinimo();
        for (int i = 1; i <= med; i++) {
            isCellEditable(i,0);
            isCellEditable(i,1);
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
    public boolean isCellEditable(int row, int column) {
        if (column == 0 || column == 1) {
            return false;
        }
        return true;
    }



    //this.validarToleranciaMedicion(listM, obj);
    public List<Mediciones>  obtenerLisMediciones (Instrumento instrumentoCalibrado, int cantidadDeMediciones) {
        List<Mediciones> lis = new ArrayList<>();
        double valorIntervalo = (double) (instrumentoCalibrado.getMaximo() - instrumentoCalibrado.getMinimo()) / cantidadDeMediciones;
        double valorM = instrumentoCalibrado.getMinimo();
        for (int i = 1; i <= cantidadDeMediciones; i++) {
            lis.add(new Mediciones(i,valorM, (Double) tablaM.getValueAt(i,2)));
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
