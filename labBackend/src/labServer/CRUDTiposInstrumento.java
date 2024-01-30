package labServer;

import Protocol.TipoInstrumentoObj;

import java.util.List;

public class CRUDTiposInstrumento {

    public void create(TipoInstrumentoObj e) throws Exception {
        System.out.println("/n ----Crea tipos de instrumento----  "+e.getNombre()+"\n");

    }

    public List<TipoInstrumentoObj> read(List<TipoInstrumentoObj> e) throws Exception {
        System.out.println("/n ----Lee la lista de tipos de instrumentos ----  \n");
        return null;
    }

    public boolean update(TipoInstrumentoObj e) throws Exception {
        System.out.println("Actualizo  tipos de instrumento.. "+e.getNombre()+"\n");
        return true;
    }

    public boolean delete(TipoInstrumentoObj e) throws Exception {
        System.out.println("Se elimino un tipo de instrumento.. "+e.getNombre()+"\n");
        return true;
    }

    public boolean delete(String e) throws Exception {
        System.out.println("Se elimino un tipo de instrumento.. "+e+"\n");
        return true;
    }
}
