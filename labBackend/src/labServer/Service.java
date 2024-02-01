
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
    public Service() {
        tiposInstrumento = new CRUDTiposInstrumento();
        instrumento = new CRUDInstrumento();
        calibraciones = new CRUDCalibraciones();
        mediciones = new CRUDMediciones();
        unidadMedida = new DAOUnidadMedida();
    }


   public void register(){
       System.out.println("Me estoy registrando -----");
   }
    //--------------Unidades de medida---------------
    @Override
    public boolean readUnidadesMedida(UnidadMedList list) throws SQLException {
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
    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception {
        System.out.print("Estoy en read en service  \n");
        return tiposInstrumento.read(e);
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
        System.out.print("Estoy en create en service  \n");
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

    //--------------------MEDICIONES---------------------
    @Override
    public void create(Mediciones medida) throws Exception {
        System.out.print("Estoy en create en service  \n");
        mediciones.create(medida);
    }

    @Override
    public boolean delete(Mediciones medida) throws Exception {
        System.out.print("Estoy en delete en service  \n");
        return mediciones.delete(medida);
    }

    @Override
    public boolean update(Mediciones medida) throws Exception {
        System.out.print("Estoy en update en service  \n");
        return mediciones.update(medida);
    }

    @Override
    public List<Mediciones> readMediciones(List<Mediciones> medida) throws Exception {
        System.out.print("Estoy en read en service  \n");
        return mediciones.read(medida);
    }
}
