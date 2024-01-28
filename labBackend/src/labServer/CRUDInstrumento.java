package labServer;

import Protocol.Instrumento;

public class CRUDInstrumento {
    public void create(Instrumento instrumento) throws Exception {
        System.out.println("Crea  instrumento.. "+ instrumento.getDescripcion()+"\n");

    }

    public Instrumento read(Instrumento instrumento) throws Exception {
        return null;
    }

    public void update(Instrumento instrumento) throws Exception {
        System.out.println("actualizo   instrumento.. "+ instrumento.getDescripcion()+"\n");


    }

    public void delete(Instrumento instrumento) throws Exception {
        System.out.println("elimino  instrumento.. "+ instrumento.getDescripcion()+"\n");

    }
}
