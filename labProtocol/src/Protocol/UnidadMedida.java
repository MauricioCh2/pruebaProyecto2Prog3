package Protocol;

import java.io.Serializable;

public class UnidadMedida implements Serializable {
    private int idUnidadMedida;
    private String nombre;
    private String simbolo;

    public UnidadMedida(){
        this.idUnidadMedida = 0;
        this.nombre = "";
        this.simbolo = "";
    }
    public UnidadMedida(int idUnidadMedida, String nombre, String simbolo) {
        this.idUnidadMedida = idUnidadMedida;
        this.nombre = nombre;
        this.simbolo = simbolo;
    }
    public int getIdUnidadMedida() {
        return idUnidadMedida;
    }
    public void setIdUnidadMedida(int idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public String toString() {
        return "UnidadMedida{" +
                "idUnidadMedida=" + idUnidadMedida +
                ", nombre='" + nombre + '\'' +
                ", simbolo='" + simbolo + '\'' +
                '}';
    }
}
