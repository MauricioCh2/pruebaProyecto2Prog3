package labPresentation.View;

import labPresentation.Controller.Instrumentos_Controller;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class InstrumentosView {
    private JPanel pnl_Instrumentos;
    private JButton btn_guardar;
    private JButton btn_limpiar;
    private JButton btn_borrar;
    private JTextField txF_Serie;
    private JTextField txF_Descripcion;
    private JTextField txF_Minimo;
    private JTextField txF_Tolerancia;
    private JTextField txF_Maximo;
    private JComboBox<String> cmB_Tipo;
    private JTextField txF_Nombre;
    private JButton btn_reporte;
    private JButton btn_buscar;
    private JTable tbl_Listado_Instrumentos;
    private JPanel pnl_ingreso;
    private JLabel lb_descripcion_buscar;
    private JCheckBox chB_busqueda;
    private JScrollPane jscroll;
    private Instrumentos_Controller controller;

    public InstrumentosView(){
        ImageIcon imageIcon = new ImageIcon("pdficon.png");
        Image scaledImage = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn_reporte.setIcon(new ImageIcon(scaledImage));

        btn_borrar.setEnabled(false);
        //this.controller = new Instrumentos_Controller(this);


    }

    public void init_controller(Instrumentos_Controller ctl){
        this.controller = ctl;
        this.initTable();
        this.initBtns_pnl_ingreso(controller);
        this.initTxtFields();
    }

    public  JPanel getPnl_istrumentos_items() {
        return  pnl_Instrumentos;
    }

    private void initTable(){
        tbl_Listado_Instrumentos.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "No. Serie",
                        "Descripcion",
                        "Minimo",
                        "Maximo",
                        "Tolerancia"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jscroll.setViewportView(tbl_Listado_Instrumentos);
        Instrumentos_Controller.Tables_Instrumento tables_Instrumento = new Instrumentos_Controller.Tables_Instrumento();
        tbl_Listado_Instrumentos.addMouseListener(tables_Instrumento);
//        DefaultTableModel modelo = new DefaultTableModel();
//
//        // Agrega una columna a la tabla
//        modelo.addColumn("No. Serie");
//        modelo.addColumn("Descripcion");
//        modelo.addColumn("Minimo");
//        modelo.addColumn("Maximo");
//        modelo.addColumn("Tolerancia");
//
//        // Asigna el modelo de datos a la tabla
//        tbl_Listado_Instrumentos.setModel(modelo);
//        tbl_Listado_Instrumentos.setVisible(true);
//        Instrumentos_Controller.Tables_Instrumento tables_Instrumento = new Instrumentos_Controller.Tables_Instrumento();
//        tbl_Listado_Instrumentos.addMouseListener(tables_Instrumento);
        // Muestra la tabla
    }

    private void initTxtFields(){
        Validaciones val = new Validaciones();
        ((AbstractDocument) txF_Descripcion.getDocument()).setDocumentFilter(val.new ValidarSinEspecialesWSpace());
        ((AbstractDocument) txF_Nombre.getDocument()).setDocumentFilter(val.new ValidarSinEspecialesWSpace());
        ((AbstractDocument) txF_Maximo.getDocument()).setDocumentFilter(val.new ValidarOnlyNumWithNegative());
        ((AbstractDocument) txF_Minimo.getDocument()).setDocumentFilter(val.new ValidarOnlyNumWithNegative());
        ((AbstractDocument) txF_Serie.getDocument()).setDocumentFilter(val.new ValidarOnlyNum());
        ((AbstractDocument) txF_Tolerancia.getDocument()).setDocumentFilter(val.new ValidarOnlyNumWithThat());
        //add more here----------------------------------------------------------------------------------------------
    }

    private void initBtns_pnl_ingreso(Instrumentos_Controller instruCntr){//instacia los botones
        //tipos instrumento
        Instrumentos_Controller.Btns_Instrumento btnsInstrumento = new Instrumentos_Controller.Btns_Instrumento();
        btn_guardar.addActionListener(btnsInstrumento);
        btn_borrar.addActionListener(btnsInstrumento);
        btn_limpiar.addActionListener(btnsInstrumento);
        btn_buscar.addActionListener(btnsInstrumento);
        btn_reporte.addActionListener(btnsInstrumento);
        //-add more
    }

    public Instrumentos_Controller getController() {
        return controller;
    }

    public JPanel getPnl_Instrumentos() {
        return pnl_Instrumentos;
    }

    public JButton getBtn_guardar() {
        return btn_guardar;
    }

    public JButton getBtn_limpiar() {
        return btn_limpiar;
    }

    public JButton getBtn_borrar() {
        return btn_borrar;
    }

    public JTextField getTxF_Serie() {
        return txF_Serie;
    }

    public JTextField getTxF_Descripcion() {
        return txF_Descripcion;
    }

    public JTextField getTxF_Minimo() {
        return txF_Minimo;
    }

    public JTextField getTxF_Tolerancia() {
        return txF_Tolerancia;
    }

    public JTextField getTxF_Maximo() {
        return txF_Maximo;
    }

    public JComboBox getCmB_Tipo() {
        return cmB_Tipo;
    }

    public JTextField getTxF_Busqueda() {return txF_Nombre;}

    public JButton getBtn_reporte() {
        return btn_reporte;
    }

    public JButton getBtn_buscar() {
        return btn_buscar;
    }

    public JTable getTbl_Listado_Instrumentos() {
        return tbl_Listado_Instrumentos;
    }

    public JCheckBox getChB_busqueda() {    return chB_busqueda; }

    public JPanel getPnl_ingreso() {
        return pnl_ingreso;
    }

}
