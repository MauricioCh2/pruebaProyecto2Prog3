package labServer;

import Protocol.Mediciones;

public class CRUDMediciones {
    //Database db;
    public void create(Mediciones med) throws Exception {
        System.out.println("Crea medicion.. "+med.getNumMedicion()+"\n");
    }

    public Mediciones read(Mediciones med) throws Exception {
        return null;
    }

    public void update(Mediciones med) throws Exception {
        System.out.println("Actualizo  medicion.. "+med.getNumMedicion()+"\n");
    }

    public void delete(Mediciones med) throws Exception {
        System.out.println("Se elimino medicion.. "+med.getNumMedicion()+"\n");

    }

}
