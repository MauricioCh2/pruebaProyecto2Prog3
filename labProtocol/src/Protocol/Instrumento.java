package Protocol;

import java.io.Serializable;

public class Instrumento implements Serializable {
    private String serie;
    private String descripcion;
    private String tipo;
    private String unidad;
    private int maximo;
    private int minimo;
    private double tolerancia;

    public Instrumento(String serie, String descripcion, String tipo, int maximo, int minimo, double tolerancia ) {
        this.serie = serie;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.maximo = maximo;
        this.minimo = minimo;
        this.tolerancia = tolerancia;

    }
    public Instrumento(String serie, String descripcion, String tipo, String maximo, String minimo, String tolerancia ) {
        this.serie = serie;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.maximo = Integer.parseInt(maximo);
        this.minimo = Integer.parseInt(minimo);
        this.tolerancia = Double.parseDouble(tolerancia);

    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public Instrumento() {
        serie = "";
        descripcion = "";
        tipo = "";
        maximo = 0;
        minimo = 0;
        tolerancia = 0.0;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public double getTolerancia() {
        return tolerancia;
    }

    public void setTolerancia(double tolerancia) {
        this.tolerancia = tolerancia;
    }
}
