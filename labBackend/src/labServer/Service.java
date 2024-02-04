
package labServer;

import Protocol.*;
import data.*;
import data.*;


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
            JOptionPane.showMessageDialog(null, "Tipo de instrumento agregado con exito!");

        }else{
            throw new Exception("ERROR: Este codigo ya existe");
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
             JOptionPane.showMessageDialog(null, "Tipo de instrumento actualizado con exito!");
         }else{
             //no se si tirar la exepcion aqui o  como ya esta en el worker
         }

    }
    @Override
    public void delete(TipoInstrumentoObj e) throws Exception {
        if (tiposInstrumento.delete(e)){
            JOptionPane.showMessageDialog(null, "Tipo de instrumento eliminado con exito!");
        }else {

        }
    }
    @Override
    public void delete(String e) throws Exception {
        if (tiposInstrumento.delete(e)){
            JOptionPane.showMessageDialog(null, "Tipo de instrumento eliminado con exito!");
        }else {

        }
    }

    //-----------------INSTRUMENTOS-------------------
    @Override
    public void create(Instrumento inst) throws Exception {
        if (instrumento.findById(inst.getSerie())== null){
            instrumento.create(inst);
            JOptionPane.showMessageDialog(null, "Instrumento agregado con exito!");
        }else{
            throw new Exception("ERROR: Este numero de serie ya existe");
        }
    }

    @Override
    public List<Instrumento> read_instrumentos( ) throws Exception {
        return instrumento.read();
    }


    @Override
    public void update(Instrumento inst) throws Exception {
        if (instrumento.update(inst)){
            JOptionPane.showMessageDialog(null, "Instrumento actualizado con exito!");
        }else{
            //no se si tirar la exepcion aqui o  como ya esta en el worker
        }
    }

    @Override
    public void delete(Instrumento inst) throws Exception {

        if (instrumento.delete(inst)){
            JOptionPane.showMessageDialog(null, "Instrumento eliminado con exito!");
        }else {

        }
    }

    @Override
    public void deleteInstrumentoId(String e) throws Exception {
        if (instrumento.delete(e)){
            JOptionPane.showMessageDialog(null, "Instrumento eliminado con exito!");
        }else {

        }
    }

    //-----------------CALIBRACIONES------------------

    @Override
    public void create(Calibraciones cali) throws Exception {
         calibraciones.create(cali);

    }

    @Override
    public List<Calibraciones> readCalibracion(String idIns) throws Exception {
        return calibraciones.read(idIns);
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
        System.out.print("Estoy en create de mediciones en service  \n");
        mediciones.create(medida);
    }

    @Override
    public List<Mediciones> read(Mediciones medida) throws Exception {
        System.out.print("Estoy en read de mediciones en service  \n");
        return mediciones.read(medida);
    }

    @Override
    public void delete(Mediciones medida) throws Exception {
        System.out.print("Estoy en delete de mediciones en service  \n");
        mediciones.delete(medida);
    }

    @Override
    public void update(Mediciones medida) throws Exception {
        System.out.print("Estoy en update de mediciones en service  \n");
        mediciones.update(medida);
    }

}
