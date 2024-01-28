package labPresentation.Model.Calibraciones;

import Protocol.Calibraciones;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableCalibraciones extends AbstractTableModel  {
    List<Calibraciones> rows;
    int[]columns;
    String[] columnasNombres = new String[3];

    //Atributos estaticos
    public static final int NumeroCalibracion = 0;
    public static final int Fecha = 1;
    public static final int CantMediciones = 2;

    @Override
    public int getRowCount() {
        return rows.size();
    }
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Calibraciones calibraciones = rows.get(rowIndex);
        switch (columns[columnIndex]){
            case NumeroCalibracion:
                //return calibraciones.getNumero();
            case Fecha:
                return calibraciones.getFecha();
            case CantMediciones:
                return calibraciones.getMedicionesL().size();
            default:
                return "";
        }
    }
    public TableCalibraciones(int[]cols,List<Calibraciones> row){
        rows = row;
        columns = cols;
        nombresColumnas();
    }
    private void nombresColumnas(){
        columnasNombres[NumeroCalibracion] = "Número";
        columnasNombres[Fecha] = "Fecha";
        columnasNombres[CantMediciones] = "Mediciones";
    }
    public Calibraciones getRowAt(int row){
        return rows.get(row);
    }
    public String getColumnName(int col){
        return columnasNombres[columns[col]];
    }
    public Class<?> getColumnClass(int col) {
        switch (columns[col]) {
            default:
                return super.getColumnClass(col);
        }
    }
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
}