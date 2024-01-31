package Protocol;

import labPresentation.Model.Calibraciones.MedicionesModel;
import labPresentation.Model.InstrumentosModel;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calibraciones implements Serializable {
    private List<Mediciones> medicionesL;
    private int numeroCalibracion;
    private String fecha;
    private int numeroMediciones;
    private Instrumento instrumento;
    private MedicionesModel myModel;

    public int getNumeroCalibracion() {
        return numeroCalibracion;
    }

    public Calibraciones(Integer integer, InstrumentosModel obj, String fecha, Integer valueOf, JTable tbM) {
        medicionesL = new ArrayList<>();
        numeroCalibracion = 0;
        this.fecha = null;
    }
    public Calibraciones(int numCal, Instrumento ins, String fecha, int numMediciones, JTable tabM) {
        this.numeroCalibracion = numCal;
        this.instrumento = ins;
        this.fecha = fecha;
        this.myModel = new MedicionesModel(tabM);
        //this.medicionesL = new ArrayList<>(numMediciones);
        this.numeroMediciones = numMediciones;
    }
    public List<Mediciones> getMedicionesL() {
        return medicionesL;
    }

    public void setMedicionesL(List<Mediciones> medicionesL) {
        this.medicionesL = medicionesL;
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
    public int getNumeroMediciones() {
        return numeroMediciones;
    }
    public String getNo_SerieIns() {
        return instrumento.getSerie();
    }
}
