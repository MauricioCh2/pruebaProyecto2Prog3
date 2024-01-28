package labServer;

import Protocol.TipoInstrumentoObj;

public class CRUDTiposInstrumento {

    public void create(TipoInstrumentoObj e) throws Exception {
        System.out.println("Crea tipos de instrumento.. "+e.getNombre()+"\n");
    }

    public TipoInstrumentoObj read(TipoInstrumentoObj e) throws Exception {

        return null;
    }

    public void update(TipoInstrumentoObj e) throws Exception {
        System.out.println("Actualizo  tipos de instrumento.. "+e.getNombre()+"\n");
    }

    public void delete(TipoInstrumentoObj e) throws Exception {
        System.out.println("Se elimino un tipo de instrumento.. "+e.getNombre()+"\n");

    }
}
