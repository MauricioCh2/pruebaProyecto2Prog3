package labServer.dao;

import Protocol.Mediciones;

import java.util.List;

public class CRUDMediciones {
    //Database db;
    public void create(Mediciones med) throws Exception {
        System.out.println("Crea medicion.. "+med.getNumMedicion()+"\n");
    }

    public List<Mediciones> read(List<Mediciones> med) throws Exception {
        return null;
    }

    public boolean update(Mediciones med) throws Exception {
        System.out.println("Actualizo  medicion.. "+med.getNumMedicion()+"\n");
        return false;
    }

    public boolean delete(Mediciones med) throws Exception {
        System.out.println("Se elimino medicion.. "+med.getNumMedicion()+"\n");
        return false;
    }

}
