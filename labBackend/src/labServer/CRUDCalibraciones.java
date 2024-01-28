package labServer;

import Protocol.Calibraciones;

public class CRUDCalibraciones {

    public void create(Calibraciones cali) throws Exception {
        System.out.println("Crea  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

    }

    public Calibraciones read(Calibraciones cali) throws Exception {
        return null;
    }

    public void update(Calibraciones cali) throws Exception {
        System.out.println("Actualiza  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

    }

    public void delete(Calibraciones cali) throws Exception {
        System.out.println("Elimina  calibracion.. "+cali.getNumeroCalibracion()+"de instrumento: "+cali.getNo_SerieIns()+"\n");

    }

}
