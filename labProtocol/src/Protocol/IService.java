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

    public Instrumento read(Instrumento instrumento) throws Exception;

    public void update(Instrumento instrumento) throws Exception;

    public void delete(Instrumento instrumento) throws Exception;


    //-----------------CALIBRACIONES------------------
    public boolean create(Calibraciones calibracion) throws Exception;

    public List<Calibraciones> read(Calibraciones calibracion) throws Exception;

    public boolean update(Calibraciones calibracion) throws Exception;

    public boolean delete(Calibraciones calibracion) throws Exception;


    //--------------------MEDIDAS---------------------
    public void create(Mediciones medida) throws Exception;

    public Mediciones read(Mediciones medida) throws Exception;

    public void update(Mediciones medida) throws Exception;

    public void delete(Mediciones medida) throws Exception;
}

