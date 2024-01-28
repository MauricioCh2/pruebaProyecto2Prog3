package labPresentation.View;

import labPresentation.Controller.TiposInstrumentoController;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class TipoInstrumentoView {
    private JButton btn_guardar;
    private JButton btn_limpiar;
    private JButton btn_borrar;
    private JTextField txF_Codigo;
    private JTextField txF_Unidad;
    private JTextField txF_Nombre;
    private JTable tbl_ListadoTipos;
    private JPanel pnl_tiposIstrumentos;
    private JPanel pnl_busqueda;
    private JButton btn_reporte;
    private JButton btn_buscar;
    private JTextField txF_buscarNombre;
    private JCheckBox chB_busqueda;
    private JLabel tx_Codigo;
    private JLabel tx_Nombre;
    private JLabel tx_Unidad;
    private JLabel tx_busqueda;
    private JScrollPane jscroll;
    private TiposInstrumentoController tiposInstrumentoController;

    //Metodos----------------------------------------------------------------------------------------------------------
    public TipoInstrumentoView() throws ParserConfigurationException, IOException, TransformerException {
        ImageIcon icon = new ImageIcon("pdf.png");
        btn_reporte.setIcon(icon);
        //btn_reporte.setIcon((Icon) icon.getImage().getScaledInstance(btn_reporte.getWidth(), btn_reporte.getHeight(), Image.SCALE_DEFAULT));

        btn_reporte.setSize(100, 50);

        initTable(); //Inicializa las funciones de la tabla y lo conecta con su controller
        btn_borrar.setEnabled(false);//no permite que se pulse el boton hasta que sea necesarioo
        //Creamos constroller
        //new TiposInstrumentoController(this); //los datos son estaticos asi que con instanciarlos es suficiente
        initBtns(); //contiene los listener de los botones
        initTxtFields(); //validaciones basicas de los textFields

        initBusqueda(); //contiene el listener de los botones de busqueda y reporte



    }
    public void init_controller(TiposInstrumentoController ctl){
        this.tiposInstrumentoController = ctl;
    }
    public  JPanel getPnl_tiposIstrumentos() {
        return  pnl_tiposIstrumentos;
    }

    private void initBtns(){//instacia los botones
        //tipos instrumento
        TiposInstrumentoController.Btns_TipoInstrumento btnsTipoInstrumento = new TiposInstrumentoController.Btns_TipoInstrumento();
        btn_guardar.addActionListener(btnsTipoInstrumento);
        btn_borrar.addActionListener(btnsTipoInstrumento);
        btn_limpiar.addActionListener(btnsTipoInstrumento);
    }

    private void initTxtFields(){
        Validaciones val = new Validaciones();
        ((AbstractDocument) txF_Codigo.getDocument()).setDocumentFilter(val.new ValidarSinEspeciales());
        ((AbstractDocument) txF_Nombre.getDocument()).setDocumentFilter(val.new ValidarSinEspecialesWSpace());
        ((AbstractDocument) txF_Unidad.getDocument()).setDocumentFilter(val.new ValidarSinEspecialesWSpace());
        ((AbstractDocument) txF_buscarNombre.getDocument()).setDocumentFilter(val.new ValidarSinEspecialesWSpace());
    }
    private void initTable(){
        tbl_ListadoTipos.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {//nombre de las columnas
                        "Codigo",
                        "Nombre",
                        "Unidad"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jscroll.setViewportView(tbl_ListadoTipos);
        TiposInstrumentoController.Tbl_tabla tbl = new TiposInstrumentoController.Tbl_tabla();
        tbl_ListadoTipos.addMouseListener(tbl);

    }
    private void initBusqueda(){
        TiposInstrumentoController.Busqueda busqueda = new TiposInstrumentoController.Busqueda();
        btn_buscar.addActionListener(busqueda);
        chB_busqueda.addActionListener(busqueda);
        btn_reporte.addActionListener(busqueda);
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

    public JTextField getTxF_Codigo() {
        return txF_Codigo;
    }

    public JTextField getTxF_Unidad() {
        return txF_Unidad;
    }

    public JTextField getTxF_Nombre() {
        return txF_Nombre;
    }

    public JTable getTbl_ListadoTipos() {
        return tbl_ListadoTipos;
    }

    public JPanel getPnl_busqueda() {
        return pnl_busqueda;
    }

    public JButton getBtn_reporte() {
        return btn_reporte;
    }

    public JButton getBtn_buscar() {
        return btn_buscar;
    }

    public JTextField getTxF_buscarNombre() {
        return txF_buscarNombre;
    }

    public JCheckBox getChB_busqueda() {
        return chB_busqueda;
    }

    public JLabel getTx_Codigo() {
        return tx_Codigo;
    }

    public JLabel getTx_Nombre() {
        return tx_Nombre;
    }

    public JLabel getTx_Unidad() {
        return tx_Unidad;
    }

    public JLabel getTx_busqueda() {
        return tx_busqueda;
    }
}
