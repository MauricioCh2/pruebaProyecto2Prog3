package Protocol;

import java.util.ArrayList;
import java.util.List;

public class Calibraciones {
    private List<Mediciones> medicionesL;

    private int numeroCalibracion;
    private String fecha;
    private static int contador = 0;
    private int numeroMediciones;
    private Instrumento instrumento;

    public int getNumeroCalibracion() {
        return numeroCalibracion;
    }

    public Calibraciones() {
        medicionesL = new ArrayList<>();
        //numero = "000";
        fecha = null;
    }



    //    public Calibraciones(String num, LocalDate fech, int numM) {
//        medicionesL = new ArrayList<>(numM);
//        numero = num;
//        fecha = fech;
//        numeroMediciones = numM;
//
//    }
    public Calibraciones(int numCal, Instrumento ins, String fecha, int numMediciones) {
        this.numeroCalibracion = numCal;
        this.instrumento = ins;
        this.fecha = fecha;
        this.medicionesL = new ArrayList<>(numMediciones);
        this.numeroMediciones = numMediciones;

    }
    public List<Mediciones> getMedicionesL() {
        return medicionesL;
    }

    public void setMedicionesL(List<Mediciones> medicionesL) {
        this.medicionesL = medicionesL;
    }





    public static int getContador() {
        return contador;
    }

    public String getFecha() {
        return fecha;
    }
    public String getStringFecha() {
        if (fecha != null) {
            return fecha.toString();
        } else {
            return "";
        }
    }
    public void setFecha (String fecha) {
        this.fecha = fecha;
    }
    @Override
    public String toString() {
        return "Calibraciones{" +
                "medicionesL=" + medicionesL +
                //", numero=" + numero +
                ", fecha=" + fecha +
                '}';
    }
    public void agregarMediciones(Mediciones mediciones){
        medicionesL.add(mediciones);
    }


    /*    public void obtenerMediciones () {
            //Rangos de mediciones
            double valorMaximo = instrumentoCalibrado.getMaximo();  //Valor máximo.
            double valorMinimo = instrumentoCalibrado.getMinimo();  //Valor mínimo.
            double valorIntervalo = (valorMaximo - valorMinimo) / cantidadDeMediciones;

            for (int i = 0; i < cantidadDeMediciones; i++) {
                double valorReferencia = valorMinimo + i * valorIntervalo;
                medicionesL.add(new Mediciones(valorReferencia, 0));
            }
        }*/
    public int getNumeroMediciones() {
        return numeroMediciones;
    }
    public String getNo_SerieIns() {
        return instrumento.getSerie();
    }
}
