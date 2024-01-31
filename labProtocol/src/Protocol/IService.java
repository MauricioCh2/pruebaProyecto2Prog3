package Protocol;

import java.util.List;

//comentario -----------------------------
public interface IService {
    public void register()throws Exception ;
    //--------------TIPOS DE INTRUMENTO---------------
    public void create(TipoInstrumentoObj e) throws Exception;

    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception;

    public boolean update(TipoInstrumentoObj e) throws Exception;

    public boolean delete(TipoInstrumentoObj e) throws Exception;
    public boolean delete(String e) throws Exception;


    //-----------------INSTRUMENTOS-------------------
    public void create(Instrumento instrumento) throws Exception;

    public List<Instrumento> read_instrumentos(Instrumento e) throws Exception;

    public boolean update(Instrumento instrumento) throws Exception;

    public boolean delete(Instrumento instrumento) throws Exception;
    public boolean delete_instrumento_id(String e) throws Exception;


    //-----------------CALIBRACIONES------------------
    public boolean create(Calibraciones calibracion) throws Exception;

    public List<Calibraciones> read(Calibraciones calibracion) throws Exception;

    public boolean update(Calibraciones calibracion) throws Exception;

    public boolean delete(Calibraciones calibracion) throws Exception;


    //--------------------MEDIDAS---------------------
    public void create(Mediciones medida) throws Exception;

    public List<Mediciones> readMediciones(List<Mediciones> medida) throws Exception;

    public boolean update(Mediciones medida) throws Exception;

    public boolean delete(Mediciones medida) throws Exception;
}

