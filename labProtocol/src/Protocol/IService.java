package Protocol;

import java.io.IOException;
import java.util.List;

//comentario -----------------------------
public interface IService {
    public void register()throws Exception ;
    public void forceUpdate() throws IOException;

    //--------------Unidades de Medida---------------
    public List<UnidadMedida> readUnidadesMedida(List<UnidadMedida> lis ) throws Exception;
    public UnidadMedida findById(int id)throws Exception;
    //--------------TIPOS DE INTRUMENTO---------------
    public void create(TipoInstrumentoObj e) throws Exception;

    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception;

    public void update(TipoInstrumentoObj e) throws Exception;

    public void delete(TipoInstrumentoObj e) throws Exception;
    public void delete(String e) throws Exception;


    //-----------------INSTRUMENTOS-------------------
    public void create(Instrumento instrumento) throws Exception;

    public List<Instrumento> read_instrumentos( ) throws Exception;

    public void update(Instrumento instrumento) throws Exception;

    public void delete(Instrumento instrumento) throws Exception;
    public void deleteInstrumentoId(String e) throws Exception;


    //-----------------CALIBRACIONES------------------
    public void create(Calibraciones calibracion) throws Exception;

    public List<Calibraciones> readCalibracion( String id ) throws Exception;

    public void update(Calibraciones calibracion) throws Exception;

    public void delete(Calibraciones calibracion) throws Exception;
    public void deleteCalibracionId(String id) throws Exception;


    //--------------------MEDICIONES---------------------
    public void create(Mediciones medida) throws Exception;

    public List<Mediciones> read(Mediciones medida) throws Exception;

    public void update(Mediciones medida) throws Exception;

    public void delete(Mediciones medida) throws Exception;



}

