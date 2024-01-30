package labServer;

import Protocol.Calibraciones;

import java.util.List;

public class CRUDCalibraciones {

    public boolean create(Calibraciones cali) throws Exception {
        System.out.println("Crea  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");
        return true;
    }

    public List<Calibraciones> read(Calibraciones cali) throws Exception {
        return null;
    }

    public boolean update(Calibraciones cali) throws Exception {
        System.out.println("Actualiza  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

        return false;
    }

    public boolean delete(Calibraciones cali) throws Exception {
        System.out.println("Elimina  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

        return false;
    }

}
