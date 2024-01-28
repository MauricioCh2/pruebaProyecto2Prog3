package labPresentation.Model;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
    final String[] columnNames = {
            "Codigo",
            "Nombre",
            "Unidad"
    };
    static Object[][] data = {};
    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public void setValueAt(Object value, int row, int col) {

    }
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }


}