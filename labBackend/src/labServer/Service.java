
package labServer;

import Protocol.*;

import java.util.List;

public class Service implements IService {

    private CRUDTiposInstrumento tiposInstrumento;
    private CRUDInstrumento instrumento;
    private CRUDCalibraciones calibraciones;
    private CRUDMediciones mediciones;
    public Service() {
        tiposInstrumento = new CRUDTiposInstrumento();
        instrumento = new CRUDInstrumento();
        calibraciones = new CRUDCalibraciones();
        mediciones = new CRUDMediciones();
    }


   public void register(){
       System.out.println("Me estoy registrando -----");
   }

    //--------------TIPOS DE INTRUMENTO---------------
    @Override
    public void create(TipoInstrumentoObj e) throws Exception {
        System.out.print("Estoy en create en service  \n");
        tiposInstrumento.create(e);

    }
    @Override
    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception {
        System.out.print("Estoy en read en service  \n");
        return tiposInstrumento.read(e);
    }
    @Override
    public boolean update(TipoInstrumentoObj e) throws Exception {
        return tiposInstrumento.update(e);

    }
    @Override
    public boolean delete(TipoInstrumentoObj e) throws Exception {
        return tiposInstrumento.delete(e);
    }
    @Override
    public boolean delete(String e) throws Exception {
        return tiposInstrumento.delete(e);
    }

    //-----------------INSTRUMENTOS-------------------
    @Override
    public void create(Instrumento inst) throws Exception {
        instrumento.create(inst);
    }

    @Override
    public List<Instrumento> read_instrumentos(List<Instrumento> e) throws Exception {
        System.out.print("Estoy en read de instrumentos en service  \n");
        return instrumento.read(e);
    }


    @Override
    public boolean update(Instrumento inst) throws Exception {
        return instrumento.update(inst);
    }

    @Override
    public boolean delete(Instrumento inst) throws Exception {
        return instrumento.delete(inst);
    }

    @Override
    public boolean delete_instrumento_id(String e) throws Exception {
        return instrumento.delete(e);
    }

    //-----------------CALIBRACIONES------------------

    @Override
    public boolean create(Calibraciones cali) throws Exception {
        return calibraciones.create(cali);

    }

    @Override
    public List<Calibraciones> read(Calibraciones cali) throws Exception {
        return calibraciones.read(cali);
    }

    @Override
    public boolean update(Calibraciones cali) throws Exception {
        return calibraciones.update(cali);
    }

    @Override
    public boolean delete(Calibraciones cali) throws Exception {
        return calibraciones.delete(cali);
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
}
