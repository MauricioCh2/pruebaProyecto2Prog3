package labPresentation.Model.Calibraciones;

import labPresentation.Model.TableModel;

import java.util.List;

public class MedicionesTableModel extends TableModel {
    List<Mediciones> mediciones;
    final String [] nombreColumnas = {
      "Número",
      "Referencia",
            "Lectura"
    };

    public MedicionesTableModel(List<Mediciones> medicionesL){
        mediciones = medicionesL;
    }
    @Override
    public int getRowCount(){
        return mediciones.size();
    }
    public int getColumnsCount(){
        return nombreColumnas.length;
    }
    public String getColumnName(int col){
        return nombreColumnas[col];
    }
    @Override
    public boolean isCellEditable(int row, int col){
        if(col ==2){
            return true;
        }else{
            return false;
        }
    }
    public Object getValuetAt(int row,int col){
       Mediciones med =  mediciones.get(row);
        switch (col){
            case 0:
                return String.valueOf(mediciones.size());
            case 1:
                return String.valueOf(med.getValorReferencia());
            case 2:
                return String.valueOf(med.getValorMarcado());
            default:
                return "";
        }
    }
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (col == 2) {
            Mediciones medicion = mediciones.get(row);
            medicion.setValorMarcado((double) value);
            fireTableCellUpdated(row, col);
        }
    }
}
