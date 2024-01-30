package labServer;

import Protocol.Instrumento;

import java.util.List;

public class CRUDInstrumento {
    public void create(Instrumento instrumento) throws Exception {
        System.out.println("Crea  instrumento.. "+ instrumento.getDescripcion()+"\n");

    }

    public List<Instrumento> read(Instrumento instrumento) throws Exception {
        System.out.println("/n ----Lee la lista de instrumentos ----  \n");
        return null;
    }

    public boolean update(Instrumento instrumento) throws Exception {
        System.out.println("actualizo   instrumento.. "+ instrumento.getDescripcion()+"\n");
        return true;
    }

    public boolean delete(Instrumento instrumento) throws Exception {
        System.out.println("elimino  instrumento.. "+ instrumento.getDescripcion()+"\n");
        return true;
    }

    public boolean delete(String e) throws Exception {
        System.out.println("Se elimino un instrumento.. "+e+"\n");
        return true;
    }

}
