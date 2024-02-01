package Protocol;

import Protocol.Listas.UnidadMedList;

import java.util.List;

//comentario -----------------------------
public interface IService {
    public void register()throws Exception ;


    //--------------Unidades de Medida---------------
    public boolean readUnidadesMedida(UnidadMedList lis ) throws Exception;
    public UnidadMedida findById(int id)throws Exception;
    //--------------TIPOS DE INTRUMENTO---------------
    public void create(TipoInstrumentoObj e) throws Exception;

    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception;

    public void update(TipoInstrumentoObj e) throws Exception;

    public void delete(TipoInstrumentoObj e) throws Exception;
    public void delete(String e) throws Exception;


    //-----------------INSTRUMENTOS-------------------
    public void create(Instrumento instrumento) throws Exception;

    public List<Instrumento> read_instrumentos(Instrumento e) throws Exception;

    public void update(Instrumento instrumento) throws Exception;

    public void delete(Instrumento instrumento) throws Exception;
    public void deleteInstrumentoId(String e) throws Exception;


    //-----------------CALIBRACIONES------------------
    public void create(Calibraciones calibracion) throws Exception;

    public List<Calibraciones> read(Calibraciones calibracion) throws Exception;

    public void update(Calibraciones calibracion) throws Exception;

    public void delete(Calibraciones calibracion) throws Exception;
    public void deleteCalibracionId(String id) throws Exception;


    //--------------------MEDIDAS---------------------
    public void create(Mediciones medida) throws Exception;

    public List<Mediciones> readMediciones(List<Mediciones> medida) throws Exception;

    public boolean update(Mediciones medida) throws Exception;

    public boolean delete(Mediciones medida) throws Exception;
}

