package Protocol;

import java.io.Serializable;
import java.util.Objects;

public class Mediciones implements Serializable {
    private double valorReferencia;  //Valor que debería ser obtenido.
    private double valorMarcado;   //Valor que marcó el instrumento.
    private int numMedicion;
    private int idCalibracion;

    public int getNumMedicion() {
        return numMedicion;
    }

    public void setNumMedicion(int numMedicion) {
        this.numMedicion = numMedicion;
    }

    public Mediciones(){
        valorReferencia = 0.0;
        valorMarcado = 0.0;
    }
    public Mediciones(double vR,double vM){
        valorReferencia = vR;
        valorMarcado = vM;
    }
    public Mediciones(int numM,double vR,double vM){
        numMedicion = numM;
        valorReferencia = vR;
        valorMarcado = vM;
    }
     public Mediciones(double vR, double vM, int cal){
        valorReferencia = vR;
        valorMarcado = vM;
        idCalibracion = cal;
    }

    public Mediciones(Mediciones med){
        this.valorMarcado = med.valorMarcado;
        this.valorReferencia = med.valorMarcado;
    }
    public double getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(double valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public double getValorMarcado() {
        return valorMarcado;
    }

    public void setValorMarcado(double valorMarcado) {
        this.valorMarcado = valorMarcado;
    }

    public int getIdCalibracion() {
        return idCalibracion;
    }

    @Override
    public String toString() {
        return "Mediciones{" +
                "valorReferencia=" + valorReferencia +
                ", valorMarcado=" + valorMarcado +
                '}';
    }
    public boolean toleranciaCorrecta(int tol){
        if(valorReferencia-tol <= valorMarcado && valorReferencia + tol >= valorMarcado){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mediciones that = (Mediciones) o;
        return Double.compare(valorReferencia, that.valorReferencia) == 0 && Double.compare(valorMarcado, that.valorMarcado) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valorReferencia, valorMarcado);
    }
}
