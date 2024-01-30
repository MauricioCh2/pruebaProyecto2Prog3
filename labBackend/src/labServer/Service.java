
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
    public Instrumento read(Instrumento inst) throws Exception {
        return instrumento.read(inst);
    }

    @Override
    public void update(Instrumento inst) throws Exception {
        instrumento.update(inst);
    }

    @Override
    public void delete(Instrumento inst) throws Exception {
        instrumento.delete(inst);
    }

    //-----------------CALIBRACIONES------------------

    @Override
    public void create(Calibraciones cali) throws Exception {
        calibraciones.create(cali);

    }

    @Override
    public Calibraciones read(Calibraciones cali) throws Exception {
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
