package Protocol;

public interface IService {
    public void register()throws Exception ;
    //--------------TIPOS DE INTRUMENTO---------------
    public void create(TipoInstrumentoObj e) throws Exception;

    public TipoInstrumentoObj read(TipoInstrumentoObj e) throws Exception;

    public void update(TipoInstrumentoObj e) throws Exception;

    public void delete(TipoInstrumentoObj e) throws Exception;


    //-----------------INSTRUMENTOS-------------------
    public void create(Instrumento instrumento) throws Exception;

    public Instrumento read(Instrumento instrumento) throws Exception;

    public void update(Instrumento instrumento) throws Exception;

    public void delete(Instrumento instrumento) throws Exception;


    //-----------------CALIBRACIONES------------------
    public void create(Calibraciones calibracion) throws Exception;

    public Calibraciones read(Calibraciones calibracion) throws Exception;

    public void update(Calibraciones calibracion) throws Exception;

    public void delete(Calibraciones calibracion) throws Exception;


    //--------------------MEDIDAS---------------------
    public void create(Mediciones medida) throws Exception;

    public Mediciones read(Mediciones medida) throws Exception;

    public void update(Mediciones medida) throws Exception;

    public void delete(Mediciones medida) throws Exception;
}

