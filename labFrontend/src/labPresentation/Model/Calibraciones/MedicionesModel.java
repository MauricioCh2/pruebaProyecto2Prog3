package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;
import Protocol.Instrumento;
import Protocol.Mediciones;
import labLogic.ServiceProxy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MedicionesModel { //
    private List<Mediciones> listM;
    JTable tablaM;

    public MedicionesModel(JTable tb) {
        this.tablaM = tb;
        listM = new ArrayList<>();
    }
    public MedicionesModel(){
        this.tablaM = new JTable();
        listM = new ArrayList<>();
    }
    public void cargar_tablaMediciones(Calibraciones calibracion, Instrumento obj, int med, int nC) throws Exception {
        //ServiceProxy.instance().deleteAll();
        DefaultTableModel model = (DefaultTableModel) tablaM.getModel();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        System.out.println(obj.toString() + "    " + med);
        System.out.println("Cont " + model.getRowCount());
        Mediciones[] v = new Mediciones[100];

        if (model.getRowCount() > 0) {
            this.limpiar_tabla(model);
        }
        if (calibracion != null && !calibracion.getMedicionesL().isEmpty()) {
            this.limpiar_tabla(model);
        }
        double valorIntervalo = (double) (obj.getMaximo() - obj.getMinimo()) / med;
        double valorM = obj.getMinimo();
        for (int i = 1; i <= med; i++) {
            System.out.println("Ejecucion del for en frontEnd----------------------------------------------------------------------------");
            Mediciones medicion = new Mediciones(i, valorM, 0.0, calibracion.getIdCalibraciones());
            //ServiceProxy.instance().create(medicion);
            v[i-1] = medicion; //-1 ya que el vector empieza en 1
            listM.add(medicion);
            Object[] fila = new Object[]{i, decimalFormat.format(valorM), null};
            model.addRow(fila);
            Object value = model.getValueAt(i - 1, 2); //aca puede hacer texto en blanco por lo que no siempre puede ser null
            if (value != null) {
                medicion.setValorMarcado(((Double) value).doubleValue());
            }
            valorM += valorIntervalo;
        }
        //ServiceProxy.instance().create(v); //manda el vector con las mediciones
        if (calibracion != null) {
            calibracion.setMedicionesL(listM);
            ServiceProxy.instance().create(listM);
        }
    }
    public List<Mediciones>  obtenerLisMediciones (Instrumento instrumentoCalibrado, int cantidadDeMediciones) {
        double valorIntervalo = (double) (instrumentoCalibrado.getMaximo() - instrumentoCalibrado.getMinimo()) / cantidadDeMediciones;
        double valorM = instrumentoCalibrado.getMinimo();
        for (int i = 1; i <= cantidadDeMediciones; i++) {
            listM.add(new Mediciones(i,valorM));
            valorM+=valorIntervalo;
        }
        return  listM;
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

    public void actualizarMediciones(JTable tab, Instrumento obj) throws Exception {
        DefaultTableModel model = (DefaultTableModel) tab.getModel();
        int rows = model.getRowCount();
        for(int i = 0;i < rows;i++){
            String v =String.valueOf(tab.getValueAt(i, 1));
            double vr = Double.parseDouble(v);

            String v2 =String.valueOf(tab.getValueAt(i, 2));
            double vm = Double.parseDouble(v2);

            int tol = (int) obj.getTolerancia();
            toleranciaCorrecta(tol, vr, vm);
        }

    }

    public void toleranciaCorrecta(int tol, double valorReferencia, double valorMarcado) throws Exception {
        double limiteI = valorReferencia - tol;
        double limiteS = valorReferencia + tol;
        if(valorMarcado < limiteI || valorMarcado>limiteS){
            throw new Exception("Valor Incorrecto\nInstrumento No Calibrado\n");
        }
    }



}
