package labPresentation.View;


import labPresentation.Controller.CalibracionesController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;


public class CalibracionesView {
    private JPanel pnl_calibraciones;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JTable tableCalibraciones;
    private JPanel medicionesPanel;
    private JTable tableMediciones;
    private JTextField textNumero;
    private JTextField textMediciones;
    private JTextField textFecha;
    private JPanel Calibracion;
    private JTextField textNumeroB;
    private JButton btn_reporte;
    private JButton buscarButton;
    private JLabel tx_instrumento;
    private CalibracionesController controlCalibraciones;

    public CalibracionesView(){

        ImageIcon imageIcon = new ImageIcon("pdficon.png");
        Image scaledImage = imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn_reporte.setIcon(new ImageIcon(scaledImage));
        textNumero.setText("000");
        textNumero.setEnabled(false);
        textFecha.setEnabled(false);
        textFecha.setText("xx/xx/xxxx");
        borrarButton.setEnabled(false);
        medicionesPanel.setVisible(false);
        this.intiPanelMediciones();


        initBotones(controlCalibraciones);
        initTable();
        initTxtFields();


    }
    public void init_controller(CalibracionesController cont){controlCalibraciones = cont;}
    private void initTable() {
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Número");
        modelo.addColumn("Fecha");
        modelo.addColumn("Mediciones");

        tableCalibraciones.setModel(modelo);
        tableCalibraciones.setVisible(true);
        CalibracionesController.TablesCalibraciones tC = new CalibracionesController.TablesCalibraciones();
        tableCalibraciones.addMouseListener(tC);

    }

    private void intiPanelMediciones(){
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Medida");
        modelo.addColumn("Referencia");
        modelo.addColumn("Lectura");

        tableMediciones.setModel(modelo);
        tableMediciones.setVisible(true);
        //CalibracionesController.TablesCalibraciones tC = new CalibracionesController.TablesCalibraciones();
        //tableCalibraciones.addMouseListener(tC);
    }


    public  JPanel getPnl_calibraciones() {
        return  pnl_calibraciones;
    }
    private void initTxtFields(){
        Validaciones val = new Validaciones();
        ((AbstractDocument) textMediciones.getDocument()).setDocumentFilter(val.new ValidarOnlyNum());
        ((AbstractDocument) textNumeroB.getDocument()).setDocumentFilter(val.new ValidarSinEspeciales());
        ((AbstractDocument) textNumeroB.getDocument()).setDocumentFilter(val.new ValidarOnlyNum());

    }

    private void initBotones(CalibracionesController control){
        CalibracionesController.BtnsCalibraciones btnsCalibraciones = new CalibracionesController.BtnsCalibraciones();
        guardarButton.addActionListener(btnsCalibraciones);
        borrarButton.addActionListener(btnsCalibraciones);
        limpiarButton.addActionListener(btnsCalibraciones);
        buscarButton.addActionListener(btnsCalibraciones);
        btn_reporte.addActionListener(btnsCalibraciones);
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JButton getLimpiarButton() {
        return limpiarButton;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public JTable getTableCalibraciones() {
        return tableCalibraciones;
    }

    public JPanel getMedicionesPanel() {
        return medicionesPanel;
    }

    public JTable getTableMediciones() {
        return tableMediciones;
    }

    public JLabel getTx_instrumento() {
        return tx_instrumento;
    }

    public JTextField getTextNumero() {
        return textNumero;
    }

    public JTextField getTextMediciones() {
        return textMediciones;
    }

    public JTextField getTextFecha() {
        return textFecha;
    }

    public JPanel getCalibracion() {
        return Calibracion;
    }

    public JTextField getTextNumeroB() {
        return textNumeroB;
    }

    public JButton getBtn_reporte() {
        return btn_reporte;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }
}
