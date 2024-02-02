
package labServer;

import Protocol.*;
import Protocol.Listas.UnidadMedList;
import labServer.dao.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Service implements IService {

    private CRUDTiposInstrumento tiposInstrumento;
    private CRUDInstrumento instrumento;
    private CRUDCalibraciones calibraciones;
    private CRUDMediciones mediciones;
    private DAOUnidadMedida unidadMedida;
    private LocalData data;
    public Service() {
        tiposInstrumento = new CRUDTiposInstrumento();
        instrumento = new CRUDInstrumento();
        calibraciones = new CRUDCalibraciones();
        mediciones = new CRUDMediciones();
        unidadMedida = new DAOUnidadMedida();
        //data = new LocalData();

    }


   public void register(){
       System.out.println("Me estoy registrando -----");
   }
    //--------------Unidades de medida---------------
    @Override
    public List<UnidadMedida> readUnidadesMedida(List<UnidadMedida> list) throws SQLException {
        System.out.println("Estoy en readUnidad en service ");
        return unidadMedida.readUnidadesMedida(list);
    }

    @Override
    public UnidadMedida findById(int id) throws Exception {
        System.out.println("Estoy en readUnidad en service ");
        return unidadMedida.findById(id);
    }

    //--------------TIPOS DE INTRUMENTO---------------
    @Override
    public void create(TipoInstrumentoObj e) throws Exception {
        System.out.print("Estoy en create en service  \n");
        if (tiposInstrumento.findById(e.getCodigo())== null){
            tiposInstrumento.create(e);
        }else{
            throw new Exception("Este elemento ya existe");
        }
    }

    @Override
    public void send_tipos_instrumento(TipoInstrumentoObj obj) {}
    @Override
    public void agregar_tipo_instrumento(TipoInstrumentoObj obj) {
        data.getListaTipos().add(obj);
        data.imprimir_lista_tipos();
    }

    @Override
    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception {
        System.out.print("Estoy en read en service  \n");
        return tiposInstrumento.read(e);
    }

    @Override
    public List<TipoInstrumentoObj> read() throws Exception {
        System.out.print("Estoy en read en service Kata 2  \n");
        return tiposInstrumento.read();
    }

    @Override
    public void update(TipoInstrumentoObj e) throws Exception {
         if (tiposInstrumento.update(e)){
             JOptionPane.showMessageDialog(null, "Tipo de instrumento actualizado con exito");
         }else{
             //no se si tirar la exepcion aqui o  como ya esta en el worker
         }

    }
    @Override
    public void delete(TipoInstrumentoObj e) throws Exception {
        if (tiposInstrumento.delete(e)){
            JOptionPane.showMessageDialog(null, "Tipo de instrumento elimina con exito");
        }else {

        }
    }
    @Override
    public void delete(String e) throws Exception {
        if (tiposInstrumento.delete(e)){
            JOptionPane.showMessageDialog(null, "Tipo de instrumento elimina con exito");
        }else {

        }
    }

    //-----------------INSTRUMENTOS-------------------
    @Override
    public void create(Instrumento inst) throws Exception {
        instrumento.create(inst);
    }

    @Override
    public List<Instrumento> read_instrumentos(Instrumento e) throws Exception {
        System.out.print("Estoy en read de instrumentos en service  \n");
        return instrumento.read(e);
    }


    @Override
    public void update(Instrumento inst) throws Exception {
         instrumento.update(inst);
    }

    @Override
    public void delete(Instrumento inst) throws Exception {
         instrumento.delete(inst);
    }

    @Override
    public void deleteInstrumentoId(String e) throws Exception {
         instrumento.delete(e);
    }

    //-----------------CALIBRACIONES------------------

    @Override
    public void create(Calibraciones cali) throws Exception {
         calibraciones.create(cali);

    }

    @Override
    public List<Calibraciones> read(Calibraciones cali) throws Exception {
        return calibraciones.read(cali);
    }

    @Override
    public void update(Calibraciones cali) throws Exception {
         calibraciones.update(cali);
    }

    @Override
    public void delete(Calibraciones cali) throws Exception {
         calibraciones.delete(cali);
    }
    public void deleteCalibracionId(String e) throws Exception {
         instrumento.delete(e);
    }

    //--------------------MEDIDAS---------------------
    @Override
    public void create(Mediciones medida) throws Exception {
        mediciones.create(medida);
    }

    @Override
    public void delete(Mediciones medida) throws Exception {
        mediciones.delete(medida);
    }

    @Override
    public void update(Mediciones medida) throws Exception {
        mediciones.update(medida);
    }

    @Override
    public Mediciones read(Mediciones medida) throws Exception {
        return mediciones.read(medida);
    }

    public List<TipoInstrumentoObj> get_lista_tipo_instrumento(){
        return data.getListaTipos();
    }
}
